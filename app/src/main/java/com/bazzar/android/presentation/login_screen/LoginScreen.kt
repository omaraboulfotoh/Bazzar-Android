package com.bazzar.android.presentation.login_screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.Constants
import com.bazzar.android.presentation.product_screen.composables.LoginScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    // receive data from previous Screen
    val args = navBackStackEntry.arguments

    //send data to viewModel
    viewModel.savedStateHandle.set(Constants.PRODUCT_KEY, product)

    // get state
    val state = viewModel.viewState()

    //handle navigation
    viewModel.sideEffect { effect ->
        when (effect) {
            is LoginContract.Effect.Navigation.goToRegisterScreen -> {}
            is LoginContract.Effect.Navigation.goToHomeAsGuest -> TODO()
            is LoginContract.Effect.Navigation.goToHome -> TODO()
        }
    }
    // init logic
    viewModel.init()
//    viewModel.categoryId=ProductScreenDestination.arguments.get()

    LoginScreenContent(state = state) { viewModel.setEvent(it) }
}

