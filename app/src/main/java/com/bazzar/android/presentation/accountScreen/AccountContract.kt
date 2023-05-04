package com.bazzar.android.presentation.accountScreen

import com.android.model.home.UserData
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class AccountContract {
    data class State(
        val isUserLoggedIn: Boolean? = false,
        val userData: UserData? = null
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnOrderHistoryClicked : Event()
        object OnWishListClicked : Event()
        object OnAddressesClicked : Event()
        object OnBazzarTermsAndConditionsClicked : Event()
        object OnAboutUsClicked : Event()
        object OnContactUs : Event()
        object OnLanguageClicked : Event()
        object OnJoinUsClicked : Event()
        object OnRewardCenterClicked : Event()
        object OnLogOutClicked : Event()
        object OnDeleteMyAccountClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object GoToOrdersHistory : Navigation()
        }
    }

}