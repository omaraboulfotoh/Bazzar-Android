package com.bazzar.android.presentation.checkOutScreen

import com.android.model.home.Area
import com.android.model.home.UserAddress
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class CheckOutContract {
    data class State(
        val selectedAddress: UserAddress? = null,
        val addressLoaded: Boolean = false,
        val areasList: List<Area> = emptyList()
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnContinueClicked : Event()
        object OnBackClicked : Event()
        object OnAddNewAddressClicked : Event()
        object OnChangeAddressClicked : Event()
        object OnSetAsDefaultClicked : Event()
        object OnDeleteAddressClicked : Event()
        object OnEditAddressClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object GoBAck : Navigation()
            object GoToAddressList : Navigation()
            data class GoToCheckout(val selectedAddress: UserAddress) : Navigation()
            data class GoToLocation(val userAddress: UserAddress? = null) : Navigation()
        }
    }

}