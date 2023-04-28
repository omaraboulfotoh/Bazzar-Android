package com.bazzar.android.presentation.login

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
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
            is LoginContract.Effect.Navigation.GoToRegisterScreen -> {}
            is LoginContract.Effect.Navigation.GoToHomeAsGuest -> TODO()
            is LoginContract.Effect.Navigation.GoToHome -> TODO()
        }
    }
    // init logic
    viewModel.init()

    LoginScreenContent(state = state) { viewModel.setEvent(it) }
}

