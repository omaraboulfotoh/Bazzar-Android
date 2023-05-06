package com.bazzar.android.presentation.accountScreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.accountScreen.composables.AccountScreenContent
import com.bazzar.android.presentation.destinations.AddressBookScreenDestination
import com.bazzar.android.presentation.destinations.LoginScreenDestination
import com.bazzar.android.presentation.destinations.OrdersHistoryScreenDestination
import com.bazzar.android.presentation.destinations.RegisterScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {


    val state = viewModel.viewState()

    viewModel.sideEffect { effect ->
        when (effect) {
            is AccountContract.Effect.Navigation.GoToOrdersHistory -> {
                navigator.navigate(OrdersHistoryScreenDestination())
            }

            is AccountContract.Effect.Navigation.GoToRegistration -> {
                navigator.navigate(LoginScreenDestination)
            }

            is AccountContract.Effect.Navigation.GoToAddressBook -> {
                navigator.navigate(AddressBookScreenDestination)
            }
        }
    }
    viewModel.initState()
    AccountScreenContent(state = state) { viewModel.setEvent(it) }
}