package com.bazzar.android.presentation.login

import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class LoginContract {
    data class State(
        var mobileNumber: String? = "",
        var password: String? = "",
        val submitButtonEnabled: Boolean = false

    ) : ViewState

    sealed class Event : ViewEvent {
        object OnLogin : Event()

        //navigation
        object OnCreateNewAccount : Event()
        object OnContinueAsAGuest : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object GoToRegisterScreen : Navigation()
            object GoToHomeAsGuest : Navigation()
            object GoToHome : Navigation()
        }
    }

}