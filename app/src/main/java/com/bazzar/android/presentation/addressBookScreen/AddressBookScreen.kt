package com.bazzar.android.presentation.addressBookScreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.addressBookScreen.composable.AddressBookScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun AddressBookScreen(
    viewModel: AddressBookViewModel = hiltViewModel(),
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
    AddressBookScreenContent(state = state) { viewModel.setEvent(it) }
}