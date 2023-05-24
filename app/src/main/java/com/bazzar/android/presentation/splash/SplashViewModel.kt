package com.bazzar.android.presentation.splash

import android.util.Log
import com.android.local.SharedPrefersManager
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.splash.SplashContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    globalState: IGlobalState,
    private val sharedPrefersManager: SharedPrefersManager
) : BaseViewModel<Event, State, Effect>(globalState) {

    override fun setInitialState() = State
    override fun handleEvents(event: Event) {
        when (event) {
            is Event.SendAnimationProgress -> handleAnimationDone(event.progress)
        }
    }

    private fun handleAnimationDone(progress: Float) {
        Log.e("FCM", sharedPrefersManager.getFcmToken().orEmpty())
        if (progress == 1f) navigateToNextStep()
    }

    private fun navigateToNextStep() =
        setEffect { if (sharedPrefersManager.isFirstTimeOpened()) Effect.Navigation.GoToOnBoarding else Effect.Navigation.GoToHome }

}