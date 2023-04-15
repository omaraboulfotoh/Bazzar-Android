package com.bazzar.android.presentation.home_screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.category_screen.CategoryScreenContent
import com.bazzar.android.presentation.destinations.CategoryScreenContentDestination
import com.bazzar.android.presentation.home_screen.composables.HomeScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {

    // get state
    val state = viewModel.viewState()
    viewModel.sideEffect { effect ->
        when (effect) {
            HomeContract.Effect.Navigation.GoToHome -> {}
            is HomeContract.Effect.Navigation.GoToSliderPage -> {}
            // category, brand
        }
    }
    // init logic
    viewModel.init()

    HomeScreenContent(state = state) { viewModel.setEvent(it) }
}