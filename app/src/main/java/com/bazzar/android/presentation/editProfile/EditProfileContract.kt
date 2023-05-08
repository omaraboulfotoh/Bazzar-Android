package com.bazzar.android.presentation.editProfile

import com.android.model.request.UserRegisterRequest
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class EditProfileContract {
    data class State(
        val request: UserRegisterRequest? = null,
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
        data class OnEmailChanged(val email: String) : Event()
        data class OnNameChanged(val name: String) : Event()
        object OnEditAccountClicked : Event()
        object OnChangePasswordClicked : Event()
        object OnBackClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object GoToChangePassword : Navigation()
            object GoBack : Navigation()
        }

    }

}