package com.bazzar.android.presentation.splash

import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class SplashContract {

    object State : ViewState

    sealed class Event : ViewEvent {
        object StartScreen : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object GoToHome : Navigation()
        }
    }
}