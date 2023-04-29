package com.bazzar.android.presentation.otp_screen

import com.android.model.home.UserData
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
        object OnSendAgainClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToHomeScreen(val token: String, val user: UserData) : Effect()
        }
    }
}

