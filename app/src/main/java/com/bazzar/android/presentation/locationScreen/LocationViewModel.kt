package com.bazzar.android.presentation.locationScreen

import com.android.model.home.UserAddress
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.locationScreen.LocationContract.Event
import com.bazzar.android.presentation.locationScreen.LocationContract.State
import com.bazzar.android.presentation.locationScreen.LocationContract.Effect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    iGlobalState: IGlobalState
) : BaseViewModel<Event, State, Effect>(iGlobalState) {
    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.OnConfirmLocationClicked -> { }
            is Event.OnBackClicked -> setEffect { Effect.Navigation.GoBack }
        }
    }

    fun init(userAddress: UserAddress?) {
        setState { copy(userAddress = userAddress) }
    }
}