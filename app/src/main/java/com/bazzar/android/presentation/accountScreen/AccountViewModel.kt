package com.bazzar.android.presentation.accountScreen

import com.android.local.SharedPrefersManager
import com.android.network.domain.usecases.HomeUseCase
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
    private val homeUseCase: HomeUseCase,
    private val sharedPrefersManager: SharedPrefersManager,
) : BaseViewModel<Event, State, Effect>(globalState) {

    private var isInitialized = false

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.OnAccountClicked -> { setEffect { Effect.Navigation.GoToEditProfile(event.userData) } }
            is Event.OnOrderHistoryClicked -> setEffect { Effect.Navigation.GoToOrdersHistory }
            is Event.OnSignupClicked -> setEffect { Effect.Navigation.GoToRegistration }
            is Event.OnAddressesClicked -> setEffect { Effect.Navigation.GoToAddressBook }
            is Event.OnFacebookClicked -> setEffect { Effect.Navigation.GoToFacebookPage }
            is Event.OnInstagramClicked -> setEffect { Effect.Navigation.GoToInstagramPage }
            is Event.OnTwitterClicked -> setEffect { Effect.Navigation.GoToTwitterPage }
            is Event.OnAboutUsClicked -> setEffect { Effect.Navigation.GoToAboutUs }
            is Event.OnBazzarTermsAndConditionsClicked -> setEffect { Effect.Navigation.GoToTermsAndConditions }
            is Event.OnContactUsClicked-> setEffect { Effect.Navigation.GoToContactUs }
            is Event.OnLogOutClicked -> { handleLogout() }
            is Event.OnDeleteMyAccountClicked -> { handleDeleteMyAccount() }
            else -> {}
        }
    }

    private fun handleLogout() = executeCatching({
        sharedPrefersManager.logout()
        setEffect { Effect.Navigation.GoToHome }
    })

    private fun handleDeleteMyAccount() = executeCatching({

    })

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