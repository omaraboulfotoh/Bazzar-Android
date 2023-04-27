package com.bazzar.android.presentation.addressBookScreen

import com.android.model.home.UserAddress
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class AddressBookContract {
    data class State(
        var counterItem: Int?,
        var addressList:List<UserAddress>
    ) : ViewState

    sealed class Event : ViewEvent {
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
        }
    }

}
