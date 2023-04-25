package com.bazzar.android.presentation.cartScreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.cartScreen.composables.CartScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {

    // get state
    val state = viewModel.viewState()
    viewModel.sideEffect { effect ->
        when (effect) {

            else -> {}
        }
    }
    // init logic
    viewModel.init()
    CartScreenContent(state = state) {

    }
}