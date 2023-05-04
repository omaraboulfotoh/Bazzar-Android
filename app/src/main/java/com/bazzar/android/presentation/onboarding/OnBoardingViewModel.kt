package com.bazzar.android.presentation.onboarding

import com.android.local.SharedPrefersManager
import com.bazzar.android.presentation.app.GlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    globalState: GlobalState,
    private val sharedPrefersManager: SharedPrefersManager
) :
    BaseViewModel<OnBoardingContract.Event, OnBoardingContract.State, OnBoardingContract.Effect>(
        globalState
    ) {
    override fun setInitialState() = OnBoardingContract.State

    override fun handleEvents(event: OnBoardingContract.Event) {
        when (event) {
            OnBoardingContract.Event.OnSkipClicked -> handleSkipAndNavigation()
        }
    }

    private fun handleSkipAndNavigation() {
        sharedPrefersManager.setFirstTimeOpened()
        setEffect { OnBoardingContract.Effect.Navigation.GoToHome }
    }

}