package com.bazzar.android.presentation.main

import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavItemDestination
import com.ramcosta.composedestinations.spec.Direction

class MainContract {
    data class State(
        val tabIndex: Int = 0,
        val direction: Direction = BottomNavItemDestination.Home.direction,
        val numOfCartItems: Int = 0,
        val isLTR: Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnNavItemSelected(val tabIndex: Int) : Event()
        object OnBackClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        object OnBackClicked : Effect()
        data class NavigateToDirection(val navItem: Direction) : Effect()
    }

    companion object {
        const val HOME_TAB = 0
        const val CATEGORIES_TAB = 1
        const val BZAAARZ_TAB = 2
        const val CART_TAB = 3
        const val PROFILE_TAB = 4
    }
}