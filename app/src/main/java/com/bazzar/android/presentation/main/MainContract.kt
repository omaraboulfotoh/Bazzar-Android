package com.bazzar.android.presentation.main

import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class MainContract {
    object State : ViewState

    sealed class Event : ViewEvent {
        data class OnNavItemSelected(val tabIndex: Int) : Event()
    }

    sealed class Effect : ViewSideEffect

    companion object {
        const val HOME_TAB = 0
        const val CATEGORIES_TAB = 1
        const val CART_TAB = 2
        const val PROFILE_TAB = 3
    }
}