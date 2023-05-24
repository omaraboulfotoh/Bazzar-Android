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

            is AddressBookContract.Event.OnDeleteAddressClicked -> {
                deleteAddress(event.index)
            }

            is AddressBookContract.Event.OnEditAddressClicked -> {
                setEffect { AddressBookContract.Effect.Navigation.GoToLocation(event.userAddress) }
            }

            is AddressBookContract.Event.OnAddAddressClicked -> {
                setEffect { AddressBookContract.Effect.Navigation.GoToLocation() }
            }
        }
    }

    private fun setAddressAsDefault(index: Int) = executeCatching({
        var address = currentState.addressList[index]
        address = address.copy(isDefault = address.isDefault?.not())
        homeUseCase.updateUserAddress(userAddress = address)
            .collect { response ->
                when (response) {
                    is Result.Error -> globalState.error(response.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> {
                        val updatedAddresses = ArrayList(currentState.addressList)
                        updatedAddresses[index] = response.data
                        setState { copy(addressList = updatedAddresses) }
                    }
                }
            }
    })

    private fun deleteAddress(index: Int) {
        // ToDO: remove or replace this logic with api logic after add it
        val addressList = ArrayList(currentState.addressList)
        addressList.removeAt(index)
        setState { copy(addressList = addressList) }
    }

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