package com.bazzar.android.presentation.aboutUs

import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class AboutUsContract {

    data class State(
        val aboutUsContent: String = "",
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnBackClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {

            object GoToBack : Navigation()
        }
    }
}