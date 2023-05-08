package com.bazzar.android.presentation.changePassword

import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class ChangePasswordContract {
    data class State(
        val currentPassword: String? = "",
        val newPassword: String? = "",
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnCurrentPasswordChanged(val currentPass: String) : Event()
        data class OnNewPasswordChanged(val newPass: String) : Event()
        object OnChangePasswordClicked : Event()
        object OnGoBack : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object GoToMainScreen : Navigation()
            object OnGoBack : Navigation()
        }
    }

}