package com.bazzar.android.presentation.createOrder

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.UserAddress
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.cartScreen.CartViewModel
import com.bazzar.android.presentation.createOrder.composables.CreateOrderScreenContent
import com.bazzar.android.presentation.destinations.SuccessScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun CreateOrderScreen(
    viewModel: CreateOrderViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    address: UserAddress
) {

    val state = viewModel.viewState()
    viewModel.sideEffect { effect ->
        when (effect) {
            CreateOrderContract.Effect.Navigation.GoBack -> navigator.navigateUp()
            CreateOrderContract.Effect.Navigation.GoToSuccessScreen -> navigator.navigate(
                SuccessScreenDestination
            )
        }
    }

    viewModel.init(address)

    CreateOrderScreenContent(state = state, onSendEvent = { viewModel.setEvent(it) })
}


