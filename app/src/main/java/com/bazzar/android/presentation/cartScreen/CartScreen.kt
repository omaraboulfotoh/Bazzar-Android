package com.bazzar.android.presentation.cartScreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.cartScreen.composables.CartScreenContent
import com.bazzar.android.presentation.destinations.AddressBookScreenDestination
import com.bazzar.android.presentation.destinations.LoginScreenDestination
import com.bazzar.android.presentation.destinations.ProductDetailScreenDestination
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
            is CartContract.Effect.Navigation.GoToProduct -> navigator.navigate(
                ProductDetailScreenDestination(product = effect.product)
            )

            CartContract.Effect.Navigation.GoToLogin -> navigator.navigate(LoginScreenDestination)
            CartContract.Effect.Navigation.GoToSelectAddress -> navigator.navigate(
                AddressBookScreenDestination
            )
        }
    }
    // init logic
    viewModel.init()
    CartScreenContent(state = state) { viewModel.setEvent(it) }
}