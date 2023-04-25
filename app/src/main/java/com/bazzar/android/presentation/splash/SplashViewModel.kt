package com.bazzar.android.presentation.splash

import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.splash.SplashContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    globalState: IGlobalState,
) : BaseViewModel<Event, State, Effect>(globalState) {

    override fun setInitialState() = State
    override fun handleEvents(event: Event) {
        when (event) {
            is Event.SendAnimationProgress -> handleAnimationDone(event.progress)
        }
    }

    private fun handleAnimationDone(progress: Float) {
        if (progress == 1f) navigateToHome()
    }

    private fun navigateToHome() = setEffect { Effect.Navigation.GoToHome }

}