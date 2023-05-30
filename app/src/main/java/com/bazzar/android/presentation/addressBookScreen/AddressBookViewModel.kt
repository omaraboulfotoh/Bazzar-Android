package com.bazzar.android.presentation.addressBookScreen

import com.android.model.home.UserAddress
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.R
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.app.ConfirmationDialogParams
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
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

            is AddressBookContract.Event.OnDeleteAddressClicked -> showDeleteDialog(
                event.index,
                event.address
            )

            is AddressBookContract.Event.OnEditAddressClicked -> {
                setEffect { AddressBookContract.Effect.Navigation.GoToLocation(event.userAddress) }
            }

            is AddressBookContract.Event.OnAddAddressClicked -> {
                setEffect { AddressBookContract.Effect.Navigation.GoToLocation() }
            }
        }
    }

    private fun showDeleteDialog(index: Int, address: UserAddress) {

        globalState.confirmationDialog(
            params = ConfirmationDialogParams(
                title = R.string.delete_address,
                description = R.string.delete_address_message,
                positiveButtonTitle = R.string.delete,
                negativeButtonTitle = R.string.cancel,
                onPositive = {
                    deleteAddress(address, index)
                }
            )
        )

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

    private fun deleteAddress(userAddress: UserAddress, index: Int) = executeCatching({
        homeUseCase.deleteAddress(userAddress.id.orZero())
            .collect {
                when (it) {
                    is Result.Error -> globalState.error(it.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> {
                        val addressList = ArrayList(currentState.addressList)
                        addressList.removeAt(index)
                        setState { copy(addressList = addressList) }
                    }
                }
            }
    })

    fun init() = executeCatching({
        homeUseCase.getAllAreas().collect { areasResponse ->
            when (areasResponse) {
                is Result.Error -> globalState.error(areasResponse.message.orEmpty())
                is Result.Loading -> globalState.loading(true)
                is Result.Success -> {
                    setState { copy(areasList = areasResponse.data.orEmpty()) }
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
                }
            }
        }
    })
}