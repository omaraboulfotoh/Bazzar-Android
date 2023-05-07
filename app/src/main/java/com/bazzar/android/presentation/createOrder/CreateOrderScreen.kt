package com.bazzar.android.presentation.createOrder

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.UserAddress
import com.bazzar.android.common.navigateAndPopCurrent
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.cartScreen.CartViewModel
import com.bazzar.android.presentation.common.WebView
import com.bazzar.android.presentation.createOrder.composables.CreateOrderScreenContent
import com.bazzar.android.presentation.destinations.SuccessScreenDestination
import com.bazzar.android.presentation.destinations.WebViewScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient


@Destination
@Composable
fun CreateOrderScreen(
    viewModel: CreateOrderViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    webViewValueRecipient: ResultRecipient<WebViewScreenDestination, Boolean>,
    address: UserAddress
) {

    val state = viewModel.viewState()
    viewModel.sideEffect { effect ->
        when (effect) {
            CreateOrderContract.Effect.Navigation.GoBack -> navigator.navigateUp()
            CreateOrderContract.Effect.Navigation.GoToSuccessScreen -> navigator.navigateAndPopCurrent(
                SuccessScreenDestination
            )

            is CreateOrderContract.Effect.Navigation.OpenWebView -> navigator.navigate(
                WebViewScreenDestination(url = effect.url)
            )
        }
    }

    viewModel.init(address)

    webViewValueRecipient.onNavResult {
        if (it is NavResult.Value) {
            viewModel.setEvent(CreateOrderContract.Event.OnPaymentCallBack(it.value))
        }
    }


    CreateOrderScreenContent(state = state, onSendEvent = { viewModel.setEvent(it) })
}


