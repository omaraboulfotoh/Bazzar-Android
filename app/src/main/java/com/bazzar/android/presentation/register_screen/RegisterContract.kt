package com.bazzar.android.presentation.register_screen

import com.android.model.home.UserData
import com.android.model.request.UserRegisterRequest
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class RegisterContract {
    data class State(
        val request: UserRegisterRequest? = null,
        val isAgreeTermsAndConditions: Boolean = false,
        val selectedGender: Gender? = null
    ) : ViewState

    sealed class Gender {
        object Male : Gender()
        object Female : Gender()
    }

    sealed class Event : ViewEvent {
        data class OnAgreeTermsAndConditions(val isClicked: Boolean) : Event()
        object OnCreateAccount : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToOtpScreen(val userData: UserData) : Navigation()
        }

    }

}