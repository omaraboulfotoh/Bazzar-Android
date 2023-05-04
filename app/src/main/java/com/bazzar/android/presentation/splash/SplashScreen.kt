package com.bazzar.android.presentation.splash

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.navigateAndClearBackStack
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.destinations.OnBoardingDestination
import com.bazzar.android.presentation.splash.composables.SplashScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val state = viewModel.viewState()

    viewModel.sideEffect { effect ->
        when (effect) {
            SplashContract.Effect.Navigation.GoToHome -> navigator.navigateAndClearBackStack(
                OnBoardingDestination()
            )
        }
    }
    SplashScreenContent(state = state, onEventSent = { viewModel.setEvent(it) })
}