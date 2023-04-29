package com.bazzar.android.presentation.register_screen

import com.android.model.home.UserData
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState
import java.util.*

class RegisterContract {
    data class State(
        var userId : Int?=-1,
        var userName: String? = "",
        var email: String? = "",
        var phoneNumber: String? = "",
        var birthDate: String? ="",
        var isAgreeTermsAndConditions: Boolean = false,
        var selectedGender: Gender? = null
    ) : ViewState

    sealed class Gender {
        object Male : Gender()
        object Female : Gender()
    }

    sealed class Event : ViewEvent {
        data class OnAgreeTermsAndConditions(val isClicked: Boolean) : Event()
        data class OnCreateAccount(val isClicked: Boolean) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToOtpScreen(val userId: Int) : Navigation()
        }

    }

}