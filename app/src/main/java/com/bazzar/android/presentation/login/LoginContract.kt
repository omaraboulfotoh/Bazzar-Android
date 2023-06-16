package com.bazzar.android.presentation.login

import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class LoginContract {
    data class State(
        val mobileNumber: String? = "",
        val password: String? = "",
        val showGuest: Boolean = true,
        val isArabic: Boolean = false,
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnLogin : Event()
        object OnForgetPassword : Event()
        data class OnPhoneChanged(val phoneNumber: String) : Event()
        data class OnPasswordChanged(val password: String) : Event()

        //navigation
        object OnCreateNewAccount : Event()
        object OnContinueAsAGuest : Event()
        object OnBackClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object GoToRegisterScreen : Navigation()
            data class GoToForgetPassword(val mobileNumber: String) : Navigation()
            object GoToHomeAsGuest : Navigation()
            object GoBack : Navigation()
        }
    }

}