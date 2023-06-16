package com.bazzar.android.presentation.contactUs

import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class ContactUsContract {

    data class State(
        val phoneNumber: String? = null,
        val email: String? = null,
        val fullName: String? = null,
        val subject: String? = null,
        val message: String? = null,
        val isArabic:Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnSubmitClicked : Event()
        object OnBackClicked : Event()
        data class OnEmailChanged(val email: String) : Event()
        data class OnPhoneChanged(val phone: String) : Event()
        data class OnNameChanged(val name: String) : Event()
        data class OnSubjectChanged(val subject: String) : Event()
        data class OnMessageChanged(val message: String) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object GoBack : Navigation()
        }

    }


}