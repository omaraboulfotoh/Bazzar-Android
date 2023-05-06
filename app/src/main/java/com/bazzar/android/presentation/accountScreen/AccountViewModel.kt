package com.bazzar.android.presentation.accountScreen

import com.android.local.SharedPrefersManager
import com.bazzar.android.presentation.accountScreen.AccountContract.Effect
import com.bazzar.android.presentation.accountScreen.AccountContract.Event
import com.bazzar.android.presentation.accountScreen.AccountContract.State
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AccountViewModel @Inject constructor(
    globalState: IGlobalState,
    private val sharedPrefersManager: SharedPrefersManager,
) : BaseViewModel<Event, State, Effect>(globalState) {

    private var isInitialized = false

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.OnOrderHistoryClicked -> setEffect { Effect.Navigation.GoToOrdersHistory }
            is Event.OnSignupClicked -> setEffect { Effect.Navigation.GoToRegistration }
            is Event.OnAddressesClicked -> setEffect { Effect.Navigation.GoToAddressBook }
            else -> {}
        }
    }

    fun initState() {
        if (isInitialized.not()) {
            setState {
                copy(
                    isUserLoggedIn = sharedPrefersManager.isUserLongedIn(),
                    userData = sharedPrefersManager.getUserData()
                )
            }
            isInitialized = false
        }
    }

}