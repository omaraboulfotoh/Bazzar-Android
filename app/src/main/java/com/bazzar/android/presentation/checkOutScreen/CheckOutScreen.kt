package com.bazzar.android.presentation.checkOutScreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.navigateAndPopCurrent
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.checkOutScreen.composables.CheckOutScreenContent
import com.bazzar.android.presentation.destinations.AddressBookScreenDestination
import com.bazzar.android.presentation.destinations.CreateOrderScreenDestination
import com.bazzar.android.presentation.destinations.LocationScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun CheckOutScreen(
    viewModel: CheckOutViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {

    // get state
    val state = viewModel.viewState()
    viewModel.sideEffect { effect ->
        when (effect) {
            is CheckOutContract.Effect.Navigation.GoBAck -> navigator.navigateUp()

            is CheckOutContract.Effect.Navigation.GoToLocation ->
                navigator.navigate(LocationScreenDestination(userAddress = effect.userAddress))

            is CheckOutContract.Effect.Navigation.GoToCheckout -> navigator.navigateAndPopCurrent(
                CreateOrderScreenDestination(effect.selectedAddress)
            )

            is CheckOutContract.Effect.Navigation.GoToAddressList -> navigator.navigate(
                AddressBookScreenDestination
            )
        }
    }
    // init logic
    viewModel.init()

    CheckOutScreenContent(state = state) { viewModel.setEvent(it) }
}