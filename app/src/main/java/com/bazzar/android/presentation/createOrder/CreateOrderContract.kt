package com.bazzar.android.presentation.createOrder

import com.android.model.home.PaymentMethod
import com.android.model.home.Product
import com.android.model.home.UserAddress
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class CreateOrderContract {
    data class State(
        val address: UserAddress? = null,
        val paymentMethodList: List<PaymentMethod>? = emptyList(),
        val subTotal: Double = 0.0,
        val shipping: Double = 0.0,
        val totalPrice: Double = 0.0,
        val discount: Double = 0.0,
        val additionalNotes: String? = null,
        val promoCode: String? = null,
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnCreateOrderClicked : Event()
        object OnBackClicked : Event()
        data class OnPaymentMethodClicked(val index: Int) : Event()
        data class OnPaymentCallBack(val status: Boolean) : Event()
        data class OnNotesChanged(val notes: String) : Event()
        data class OnPromoCodeChanged(val promoCode: String) : Event()
        object OnPromoCodeSubmit : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object GoToSuccessScreen : Navigation()
            object GoBack : Navigation()
            data class OpenWebView(val url: String) : Navigation()
        }
    }


}