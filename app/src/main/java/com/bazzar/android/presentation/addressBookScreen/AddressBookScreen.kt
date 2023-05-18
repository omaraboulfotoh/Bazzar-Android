package com.bazzar.android.presentation.addressBookScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.addressBookScreen.composable.AddressBookScreenContent
import com.bazzar.android.presentation.destinations.LocationScreenDestination
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

    LaunchedEffect(Unit) {
        // init logic
        viewModel.init()
    }

    viewModel.sideEffect { effect ->
        when (effect) {
            is AddressBookContract.Effect.Navigation.GoToLocation -> {
                navigator.navigate(LocationScreenDestination(userAddress = effect.userAddress))
            }

            is AddressBookContract.Effect.Navigation.GoToBack -> {
                navigator.navigateUp()
            }
        }
    }

    AddressBookScreenContent(state = state) { viewModel.setEvent(it) }
}