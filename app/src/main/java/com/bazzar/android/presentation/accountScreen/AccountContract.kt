package com.bazzar.android.presentation.accountScreen

import com.android.model.home.UserData
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class AccountContract {
    data class State(
        val isUserLoggedIn: Boolean = false,
        val userData: UserData? = null,
        val showWishList: Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnAccountClicked(val userData: UserData) : Event()
        object OnOrderHistoryClicked : Event()
        object OnWishListClicked : Event()
        object OnAddressesClicked : Event()
        object OnBazzarTermsAndConditionsClicked : Event()
        object OnAboutUsClicked : Event()
        object OnVendorClicked : Event()
        object OnContactUsClicked : Event()
        object OnMarketerClicked : Event()
        object OnLanguageClicked : Event()
        object OnJoinUsClicked : Event()
        object OnRewardCenterClicked : Event()
        object OnInstagramClicked : Event()
        object OnFacebookClicked : Event()
        object OnTwitterClicked : Event()
        object OnLogOutClicked : Event()
        object OnDeleteMyAccountClicked : Event()
        object OnSignupClicked : Event()
        object OnTackToUsClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToEditProfile(val userData: UserData) : Navigation()
            data class OpenLink(val link: String) : Navigation()
            object GoToOrdersHistory : Navigation()
            object GoToWishList : Navigation()
            object GoToRegistration : Navigation()
            object GoToAddressBook : Navigation()
            object GoToAboutUs : Navigation()
            object GoToTermsAndConditions : Navigation()
            object GoToContactUs : Navigation()
            data class CallSupport(val phone: String) : Navigation()
            object GoToFacebookPage : Navigation()
            object GoToInstagramPage : Navigation()
            object GoToTwitterPage : Navigation()
            object OnRestartApp : Navigation()
        }
    }

}