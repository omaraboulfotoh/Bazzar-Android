package com.bazzar.android.presentation.login_screen

import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class LoginContract {
    data class State(
        var isValidNumber: Boolean?=false
    ) : ViewState

    sealed class Event : ViewEvent {
        // update state + navigation
        data class OnLogin(val phoneNumber: String) : Event()
        //navigation
        object OnCreateNewAccount : Event()
        object OnContinueAsAGuest : Event()
    }
    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object goToRegisterScreen : Navigation()
            object goToHomeAsGuest : Navigation()
            data class goToHome(val brandId: Int) : Navigation()
        }

    }

}