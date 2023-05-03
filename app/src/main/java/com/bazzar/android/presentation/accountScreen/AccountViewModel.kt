package com.bazzar.android.presentation.accountScreen

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
) : BaseViewModel<Event, State, Effect>(globalState) {
    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            Event.OnAboutUsClicked -> {}
            Event.OnAddressesClicked -> {}
            Event.OnBazzarTermsAndConditionsClicked -> {}
            Event.OnContactUs -> {}
            Event.OnDeleteMyAccountClicked -> {}
            Event.OnJoinUsClicked -> {}
            Event.OnLanguageClicked -> {}
            Event.OnLogOutClicked -> {}
            Event.OnOrderHistoryClicked -> {}
            Event.OnRewardCenterClicked -> {}
            Event.OnWishListClicked -> {}
            Event.OnSignupClicked -> {
                setEffect { Effect.Navigation.GoToSignup }
            }
        }
    }
}