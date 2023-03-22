package com.bazzar.android.presentation.splash

import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.splash.SplashContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds


@HiltViewModel
class SplashViewModel @Inject constructor(
    globalState: IGlobalState,
) : BaseViewModel<Event, State, Effect>(globalState) {

    override fun setInitialState() = State
    override fun handleEvents(event: Event) {
        when (event) {
            Event.StartScreen -> navigateToHome()
        }
    }

    private fun navigateToHome() = executeCatching({
        delay(2.seconds)
        setEffect { Effect.Navigation.GoToHome }
    })

}