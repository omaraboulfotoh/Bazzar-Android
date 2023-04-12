package com.bazzar.android.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.bazzar.android.common.sideEffect
import com.bazzar.android.presentation.NavGraphs
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.composables.bottomNavigation.MBCBottomNavigation
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

    // handle effects
    viewModel.sideEffect { effect ->
    }
    // Get bottom bar nav controller
    val navController = rememberNavController()

    // Render scaffold to put bottom bar
    Scaffold(
        bottomBar = {
            MBCBottomNavigation(
                navController = navController,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(BottomNavigationHeight),
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
