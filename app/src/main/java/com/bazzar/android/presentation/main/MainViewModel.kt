package com.bazzar.android.presentation.main

import com.bazzar.android.R
import com.bazzar.android.presentation.app.ConfirmationDialogParams
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavItemDestination
import com.bazzar.android.presentation.destinations.CategoryScreenDestination
import com.bazzar.android.presentation.eventbus.EventBus
import com.bazzar.android.presentation.eventbus.MainEvent
import com.bazzar.android.presentation.main.MainContract.Effect
import com.bazzar.android.presentation.main.MainContract.Event
import com.bazzar.android.presentation.main.MainContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    globalState: IGlobalState,
) : BaseViewModel<Event, State, Effect>(globalState) {

    init {
        executeCatching({
            EventBus.subscribe<MainEvent> { event ->
                when (event) {
                    is MainEvent.ChangeBottomTap -> {
                        triggerTapClicked(event.tapIndex, event.additionalValue, true)
                    }
                }
            }
        })
    }

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.OnNavItemSelected -> triggerTapClicked(event.tabIndex)
            Event.OnBackClicked -> handleBackAction()
        }
    }

    private fun handleBackAction() {
        if (currentState.tabIndex != 0) {
            setState { copy(tabIndex = 0, direction = BottomNavItemDestination.Home.direction) }
            setEffect { Effect.NavigateToDirection(BottomNavItemDestination.Home.direction) }
        } else {
            globalState.confirmationDialog(params = ConfirmationDialogParams(
                title = R.string.exit,
                description = R.string.exit_description,
                positiveButtonTitle = R.string.yes,
                negativeButtonTitle = R.string.cancel,
                onPositive = {
                    setEffect { Effect.OnBackClicked }
                }
            ))

        }
    }

    private fun triggerTapClicked(
        tabIndex: Int,
        additionalValue: Boolean? = null,
        handleItemNavigation: Boolean = false
    ) {
        val direction = when (tabIndex) {
            MainContract.CATEGORIES_TAB -> CategoryScreenDestination(additionalValue ?: true)
            MainContract.BZAAARZ_TAB -> BottomNavItemDestination.Bazzars.direction
            MainContract.CART_TAB -> BottomNavItemDestination.Cart.direction
            MainContract.PROFILE_TAB -> BottomNavItemDestination.Profile.direction
            else -> BottomNavItemDestination.Home.direction
        }
        setState { copy(tabIndex = tabIndex, direction = direction) }
        if (handleItemNavigation)
            setEffect { Effect.NavigateToDirection(direction) }

    }
}
