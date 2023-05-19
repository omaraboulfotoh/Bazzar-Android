package com.bazzar.android.presentation.checkOutScreen

import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.common.orFalse
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.checkOutScreen.CheckOutContract.Effect
import com.bazzar.android.presentation.checkOutScreen.CheckOutContract.Event
import com.bazzar.android.presentation.checkOutScreen.CheckOutContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckOutViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
) : BaseViewModel<Event, State, Effect>(
    globalState
) {

    private var isInitialized = false
    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            Event.OnAddNewAddressClicked -> setEffect { Effect.Navigation.GoToAddNewAddress }
            Event.OnContinueClicked -> navigateToCheckout()
            Event.OnBackClicked -> setEffect { Effect.Navigation.GoBAck }
            Event.OnChangeAddressClicked -> setEffect { Effect.Navigation.GoToAddressList }
        }
    }

    private fun navigateToCheckout() {
        val selectedAddress = currentState.selectedAddress ?: return
        setEffect { Effect.Navigation.GoToCheckout(selectedAddress) }
    }

    fun init() {
        if (isInitialized.not()) {
            loadAddress()
            isInitialized = false
        }
    }

    private fun loadAddress() = executeCatching({
        homeUseCase.getAllAddresses().collect {
            when (it) {
                is Result.Error -> globalState.error(it.message.orEmpty())
                is Result.Loading -> globalState.loading(true)
                is Result.Success -> {
                    val selectedAddress = it.data.orEmpty().firstOrNull { it.isDefault.orFalse() }
                        ?: it.data.orEmpty().firstOrNull()
                    setState { copy(selectedAddress = selectedAddress) }
                }
            }
        }
    })
}