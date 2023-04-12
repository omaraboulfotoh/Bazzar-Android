package com.bazzar.android.presentation.main

import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.main.MainContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    globalState: IGlobalState,
) : BaseViewModel<Event, State, Effect>(globalState) {

    override fun setInitialState() = State

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.OnNavItemSelected -> triggerTapClicked(event.tabIndex)
        }
    }


    // todo will need this if we have analytics or custom events
    private fun triggerTapClicked(tabIndex: Int) {}
}
