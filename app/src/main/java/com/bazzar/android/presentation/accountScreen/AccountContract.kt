package com.bazzar.android.presentation.accountScreen

import com.android.model.home.UserData
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class AccountContract {
    data class State(
        val isUserLoggedIn: Boolean = false,
        val userData: UserData? = null
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnOrderHistoryClicked : Event()
        object OnWishListClicked : Event()
        object OnAddressesClicked : Event()
        object OnBazzarTermsAndConditionsClicked : Event()
        object OnAboutUsClicked : Event()
        object OnContactUsClicked : Event()
        object OnLanguageClicked : Event()
        object OnJoinUsClicked : Event()
        object OnRewardCenterClicked : Event()
        object OnInstagramClicked : Event()
        object OnFacebookClicked : Event()
        object OnTwitterClicked : Event()
        object OnLogOutClicked : Event()
        object OnDeleteMyAccountClicked : Event()
        object OnSignupClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object GoToOrdersHistory : Navigation()
            object GoToRegistration : Navigation()
            object GoToAddressBook : Navigation()
            object GoToHome : Navigation()
            object GoToAboutUs: Navigation()
            object GoToTermsAndConditions: Navigation()
            object GoToContactUs: Navigation()
            object GoToFacebookPage: Navigation()
            object GoToInstagramPage: Navigation()
            object GoToTwitterPage: Navigation()
        }
    }

}