package com.bazzar.android.presentation.register

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.destinations.OtpScreenDestination
import com.bazzar.android.presentation.register.composables.RegisterScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    // get state
    val state = viewModel.viewState()
    viewModel.sideEffect { effect ->
        when (effect) {
            is RegisterContract.Effect.Navigation.GoToOtpScreen -> navigator.navigate(
                OtpScreenDestination(effect.userData)
            )

            RegisterContract.Effect.Navigation.GoBack -> navigator.navigateUp()
        }
    }
    // init logic
    viewModel.init()

    RegisterScreenContent(state = state) { viewModel.setEvent(it) }
}
