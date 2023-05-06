package com.bazzar.android.presentation.otpScreen

import com.android.model.home.UserData
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class OtpContract {
    data class State(
        val otp: String? = "",
        val userData: UserData? = null,
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnConfirmClicked : Event()
        object OnSendAgain : Event()
        data class OnOtpChanged(val otp: String) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object GoToHomeScreen : Effect()
        }
    }
}

