package com.bazzar.android.presentation.otp_screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.UserData
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.NavGraphs
import com.bazzar.android.presentation.destinations.MainScreenDestination
import com.bazzar.android.presentation.otp_screen.composables.OtpScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo

@Destination
@Composable
fun OtpScreen(
    viewModel: OtpViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    userData: UserData
) {
    val state = viewModel.viewState()
    viewModel.sideEffect { effect ->
        when (effect) {
            is OtpContract.Effect.Navigation.GoToHomeScreen -> {
                navigator.navigate(MainScreenDestination.route) {
                    popUpTo(NavGraphs.root) { saveState = false }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }
    // init logic
    viewModel.init(userData)

    OtpScreenContent(state = state) { viewModel.setEvent(it) }
}