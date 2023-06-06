package com.bazzar.android.presentation.checkOutScreen

import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.common.orFalse
import com.bazzar.android.common.orZero
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
            Event.OnAddNewAddressClicked -> setEffect { Effect.Navigation.GoToLocation() }
            Event.OnContinueClicked -> navigateToCheckout()
            Event.OnBackClicked -> setEffect { Effect.Navigation.GoBAck }
            Event.OnChangeAddressClicked -> setEffect { Effect.Navigation.GoToAddressList }
            Event.OnEditAddressClicked -> setEffect { Effect.Navigation.GoToLocation(currentState.selectedAddress) }
            Event.OnDeleteAddressClicked -> deleteAddress()
            Event.OnSetAsDefaultClicked -> setAddressAsDefault()
        }
    }

    private fun setAddressAsDefault() = executeCatching({
        currentState.selectedAddress?.copy(
            isDefault = currentState.selectedAddress?.isDefault.orFalse().not()
        )?.let { address ->
            homeUseCase.updateUserAddress(userAddress = address).collect { response ->
                when (response) {
                    is Result.Error -> globalState.error(response.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> setState { copy(selectedAddress = response.data) }
                }
            }
        }
    })

    private fun deleteAddress() = executeCatching({
        currentState.selectedAddress?.let { address ->
            homeUseCase.deleteAddress(address.id.orZero()).collect {
                when (it) {
                    is Result.Error -> globalState.error(it.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> setState { copy(selectedAddress = null) }
                }
            }
        }
    })

    private fun navigateToCheckout() {
        val selectedAddress = currentState.selectedAddress ?: return
        setEffect { Effect.Navigation.GoToCheckout(selectedAddress) }
    }

    fun init() = executeCatching({
        if (isInitialized.not()) {
            homeUseCase.getAllAreas().collect { areasResponse ->
                when (areasResponse) {
                    is Result.Error -> globalState.error(areasResponse.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> {
                        setState { copy(areasList = areasResponse.data.orEmpty()) }
                        loadAddress()
                    }
                }
            }
            isInitialized = true
        } else {
            loadAddress()
        }
    })

    private fun loadAddress() = executeCatching({
        homeUseCase.getAllAddresses().collect {
            when (it) {
                is Result.Error -> {
                    globalState.error(it.message.orEmpty())
                    setState { copy(addressLoaded = true) }
                }

                is Result.Loading -> globalState.loading(true)
                is Result.Success -> {
                    val selectedAddress = it.data.orEmpty().firstOrNull { it.isDefault.orFalse() }
                        ?: it.data.orEmpty().firstOrNull()
                    setState { copy(selectedAddress = selectedAddress, addressLoaded = true) }
                }
            }
        }
    })
}