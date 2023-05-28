package com.bazzar.android.presentation.forgetPassword

import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class ForgetPasswordContract {
    data class State(
        val phoneNumber: String? = null,
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnBackClicked : Event()
        object OnSendClicked : Event()
        data class OnPhoneChanged(val phone: String) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object GoBack : Navigation()
        }

    }
}