package com.bazzar.android.presentation.homeScreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.UserData
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.destinations.ProductScreenDestination
import com.bazzar.android.presentation.homeScreen.composables.HomeScreenContent
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
            is HomeContract.Effect.Navigation.GoToBrandProductsList -> navigator.navigate(
                ProductScreenDestination(brand = effect.brand)
            )

            is HomeContract.Effect.Navigation.GoToCategoryProductsList -> navigator.navigate(
                ProductScreenDestination(category = effect.category)
            )
        }
    }
    // init logic
    viewModel.init()
    HomeScreenContent(state = state) { viewModel.setEvent(it) }
}