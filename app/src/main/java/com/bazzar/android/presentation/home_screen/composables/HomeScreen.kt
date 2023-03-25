package com.bazzar.android.presentation.home_screen.composables

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.home_screen.HomeContract
import com.bazzar.android.presentation.home_screen.HomeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val state = viewModel.viewState()
    viewModel.setEvent(HomeContract.Event.StartScreen)
    viewModel.sideEffect { effect ->
        when (effect) {
            HomeContract.Effect.Navigation.GoToHome -> {}
        }
    }
    HomeScreenContent()
}