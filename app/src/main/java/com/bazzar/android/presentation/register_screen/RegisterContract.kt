package com.bazzar.android.presentation.register_screen

import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class RegisterContract {
    data class State(
        var isValidNumber: Boolean
    ) : ViewState

    sealed class Event : ViewEvent {
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
        }

    }

}