package com.bazzar.android.presentation.main

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bazzar.android.common.getActivity
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.NavGraphs
import com.bazzar.android.presentation.app.MainActivity
import com.bazzar.android.presentation.composables.bottomNavigation.MBCBottomNavigation
import com.bazzar.android.presentation.composables.bottomNavigation.onNavItemClick
import com.bazzar.android.presentation.destinations.HomeScreenDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    selectedTabIndex: Int = MainContract.HOME_TAB,
) {

    val state = viewModel.viewState()
    val navController = rememberNavController()
    val context = LocalContext.current
    val activity = LocalContext.current.getActivity<MainActivity>()!!

    // handle effects
    viewModel.sideEffect { effect ->
        when (effect) {
            MainContract.Effect.OnBackClicked -> if (navigator.popBackStack().not()) {
                activity.finish()
            }
            is MainContract.Effect.NavigateToDirection -> {
                navController.onNavItemClick(effect.navItem)
            }
        }
    }
    // Get bottom bar nav controller

    BackHandler {
        viewModel.setEvent(MainContract.Event.OnBackClicked)
    }

    // Render scaffold to put bottom bar
    Scaffold(
        bottomBar = {
            MBCBottomNavigation(
                navController = navController,
                modifier = Modifier.fillMaxWidth(),
                selectedDestination = state.direction,
                selectedIndex = state.tabIndex,
                onTabSelected = {
                    viewModel.setEvent(MainContract.Event.OnNavItemSelected(it))
                }
            )
        }
    ) {
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController,
            startRoute = when (selectedTabIndex) {
                MainContract.CATEGORIES_TAB -> HomeScreenDestination
                MainContract.CART_TAB -> HomeScreenDestination
                MainContract.PROFILE_TAB -> HomeScreenDestination
                else -> HomeScreenDestination
            }
        )
    }
}
