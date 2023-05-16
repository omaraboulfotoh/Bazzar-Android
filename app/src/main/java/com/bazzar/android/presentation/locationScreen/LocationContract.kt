package com.bazzar.android.presentation.locationScreen

import com.android.model.home.UserAddress
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class LocationContract {
    data class State(
        val userAddress: UserAddress? = null
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnConfirmLocationClicked(val userAddress: UserAddress) : Event()
        object OnBackClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToEditOrAddAddress(val userAddress: UserAddress) : Navigation()
            object GoBack : Navigation()
        }
    }
}