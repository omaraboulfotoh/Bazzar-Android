package com.bazzar.android.presentation.onboarding

import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class OnBoardingContract {

    object State : ViewState

    sealed class Event : ViewEvent {
        object OnSkipClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object GoToHome : Navigation()
        }
    }
}