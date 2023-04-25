package com.bazzar.android.presentation.addAddressScreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.addAddressScreen.composables.AddressScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun AddressScreen(
    viewModel: AddressViewModel = hiltViewModel(),
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
    AddressScreenContent(state = state) { viewModel.setEvent(it) }
}