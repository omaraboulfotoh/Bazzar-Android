package com.bazzar.android.presentation.addAddressScreen

import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class AddressContract {

    data class State(
        var counterItem: Int?
    ) : ViewState

    sealed class Event : ViewEvent {
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
        }
    }

}