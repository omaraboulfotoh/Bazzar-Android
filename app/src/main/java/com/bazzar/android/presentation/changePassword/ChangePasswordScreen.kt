package com.bazzar.android.presentation.changePassword

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.navigateAndClearBackStack
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.changePassword.ChangePasswordContract.Effect
import com.bazzar.android.presentation.changePassword.composables.ChangePasswordScreenContent
import com.bazzar.android.presentation.destinations.MainScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun ChangePasswordScreen(
    viewModel: ChangePasswordViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val state = viewModel.viewState()

    viewModel.sideEffect { effect ->
        when (effect) {
            is Effect.Navigation.GoToMainScreen ->
                navigator.navigateAndClearBackStack(MainScreenDestination())
            is Effect.Navigation.OnGoBack -> navigator.navigateUp()
        }
    }

    ChangePasswordScreenContent(state = state) { viewModel.setEvent(it) }
}