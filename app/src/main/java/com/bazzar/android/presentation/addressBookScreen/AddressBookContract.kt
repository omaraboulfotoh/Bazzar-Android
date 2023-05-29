package com.bazzar.android.presentation.addressBookScreen

import com.android.model.home.UserAddress
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class AddressBookContract {
    data class State(
        var counterItem: Int = 0,
        var addressList: List<UserAddress> = emptyList(),
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnSetAsDefaultClicked(val index: Int) : Event()
        data class OnDeleteAddressClicked(val address: UserAddress, val index: Int) : Event()
        data class OnEditAddressClicked(val userAddress: UserAddress) : Event()
        object OnAddAddressClicked : Event()
        object OnBackIconClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToLocation(val userAddress: UserAddress? = null) : Navigation()
            object GoToBack : Navigation()
        }
    }

}
