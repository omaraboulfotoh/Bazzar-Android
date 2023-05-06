package com.bazzar.android.presentation.login

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.destinations.RegisterScreenDestination
import com.bazzar.android.presentation.login.composables.LoginScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {

    // get state
    val state = viewModel.viewState()

    //handle navigation
    viewModel.sideEffect { effect ->
        when (effect) {
            is LoginContract.Effect.Navigation.GoToRegisterScreen -> navigator.navigate(
                RegisterScreenDestination
            )

            is LoginContract.Effect.Navigation.GoToHomeAsGuest -> navigator.navigateUp()
            is LoginContract.Effect.Navigation.GoBack -> navigator.navigateUp()
        }
    }
    // init logic
    viewModel.init()

    LoginScreenContent(state = state) { viewModel.setEvent(it) }
}

