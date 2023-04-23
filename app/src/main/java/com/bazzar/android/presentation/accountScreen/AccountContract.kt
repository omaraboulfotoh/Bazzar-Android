package com.bazzar.android.presentation.accountScreen

import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class AccountContract {
    data class State(
        var isValidNumber: Boolean? = false
    ) : ViewState

    sealed class Event : ViewEvent {

    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {

        }

    }

}