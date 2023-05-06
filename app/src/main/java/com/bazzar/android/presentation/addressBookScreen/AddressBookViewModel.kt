package com.bazzar.android.presentation.addressBookScreen

import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressBookViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
) : BaseViewModel<AddressBookContract.Event, AddressBookContract.State, AddressBookContract.Effect>(
    globalState
) {
    override fun setInitialState() = AddressBookContract.State()

    override fun handleEvents(event: AddressBookContract.Event) {
        when (event) {
            is AddressBookContract.Event.OnBackIconClicked -> {
                setEffect { AddressBookContract.Effect.Navigation.GoToBack }
            }

            is AddressBookContract.Event.OnSetAsDefaultClicked -> {
                setAddressAsDefault(event.index)
            }

            is AddressBookContract.Event.OnEditAddressClicked -> {
                setEffect { AddressBookContract.Effect.Navigation.GoToEditAddress(event.userAddress) }
            }

            is AddressBookContract.Event.OnAddAddressClicked -> {
                setEffect { AddressBookContract.Effect.Navigation.GoToAddressScreen }
            }
        }
    }

    private fun setAddressAsDefault(index: Int) = executeCatching({
        val updatedAddresses = currentState.addressList
            .mapIndexed { i, address -> address.copy(isDefault = i == index) }
        setState { copy(addressList = updatedAddresses) }
        homeUseCase.updateUserAddress(updatedAddresses[index])
            .collect { }
    })

    fun init() = executeCatching({
        homeUseCase.getAllAddresses()
            .collect { addressesResponse ->
                when (addressesResponse) {
                    is Result.Error -> globalState.error(addressesResponse.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> {
                        setState {
                            copy(addressList = addressesResponse.data.orEmpty())
                        }
                    }
                }
            }
    })
}