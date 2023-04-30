package com.bazzar.android.presentation.otp_screen

import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class OtpContract {
    data class State(
        var otp: String? = "",
        var timer: String? = ""
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnConfirmClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object GoToHomeScreen : Effect()
        }
    }
}

