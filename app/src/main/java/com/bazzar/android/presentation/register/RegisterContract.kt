package com.bazzar.android.presentation.register

import com.android.model.home.UserData
import com.android.model.request.UserRegisterRequest
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class RegisterContract {
    data class State(
        val request: UserRegisterRequest? = null,
        val isAgreeTermsAndConditions: Boolean = false,
        val selectedGender: Gender? = null,
        val phoneNumber: String? = null,
        val email: String? = null,
        val fullName: String? = null,
    ) : ViewState

    sealed class Gender {
        object Male : Gender()
        object Female : Gender()
    }

    sealed class Event : ViewEvent {
        object OnAgreeTermsAndConditions : Event()
        object OnCreateAccount : Event()
        object OnBackClicked : Event()
        data class OnEmailChanged(val email: String) : Event()
        data class OnPhoneChanged(val phone: String) : Event()
        data class OnNameChanged(val name: String) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToOtpScreen(val userData: UserData) : Navigation()
            object GoBack : Navigation()
        }

    }

}