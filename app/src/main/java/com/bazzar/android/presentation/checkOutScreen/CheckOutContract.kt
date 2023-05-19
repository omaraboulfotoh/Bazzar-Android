package com.bazzar.android.presentation.checkOutScreen

import com.android.model.home.UserAddress
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class CheckOutContract {
    data class State(
        val selectedAddress: UserAddress? = null
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnContinueClicked : Event()
        object OnBackClicked : Event()
        object OnAddNewAddressClicked : Event()
        object OnChangeAddressClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object GoBAck : Navigation()
            object GoToAddNewAddress : Navigation()
            object GoToAddressList : Navigation()
            data class GoToCheckout(val selectedAddress: UserAddress) : Navigation()
        }
    }

}