package com.bazzar.android.presentation.addAddressScreen

import com.android.local.SharedPrefersManager
import com.android.model.home.Area
import com.android.model.home.UserAddress
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.MapLatLngConstants
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val prefersManager: SharedPrefersManager,
) : BaseViewModel<AddressContract.Event, AddressContract.State, AddressContract.Effect>(globalState) {
    override fun setInitialState() = AddressContract.State()

    override fun handleEvents(event: AddressContract.Event) {
        when (event) {
            is AddressContract.Event.OnFlatNumberChanged -> setState { copy(flatNumber = event.flatNumber) }
            is AddressContract.Event.OnHouseNumberChanged -> setState { copy(houseNumber = event.houseNumber) }
            is AddressContract.Event.OnBlockChanged -> setState { copy(block = event.block) }
            is AddressContract.Event.OnNotesChanged -> setState { copy(notes = event.notes) }
            is AddressContract.Event.OnStreetNameChanged -> setState { copy(streetName = event.streetName) }
            is AddressContract.Event.OnToggleChanged -> setState { copy(toggleEnabled = event.togelEnabled) }
            is AddressContract.Event.OnAreaSelected -> {
                handleOnAreaSelected(event.area)
            }

            is AddressContract.Event.OnGovernmentSelected -> {
                handleOnGovernmentSelected(event.government)
            }

            is AddressContract.Event.OnBackIconClicked -> {
                setEffect { AddressContract.Effect.Navigation.GoToBack }
            }

            is AddressContract.Event.OnSaveButtonClicked -> {
                addOrEditAddress()
            }

            is AddressContract.Event.OnJadaValueChanged -> setState { copy(jada = event.jada) }
        }
    }

    private fun handleOnAreaSelected(area: Area) {
        setState { copy(selectedArea = area) }
    }

    private fun handleOnGovernmentSelected(government: Area) {
        val matchedAreas =
            currentState.allGovernmentsAndAreas.filter { it.parentId == government.id }
        setState {
            copy(
                selectedGovernment = government,
                areas = matchedAreas,
                selectedArea = null
            )
        }
    }

    fun init(userAddress: UserAddress) = executeCatching({
        homeUseCase.getAllAreas()
            .collect { areasResponse ->
                when (areasResponse) {
                    is Result.Error -> globalState.error(areasResponse.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> {
                        val allGovernmentsAndAreas = areasResponse.data.orEmpty()
                        val governments = allGovernmentsAndAreas.filter { it.parentId == null }
                        val areas = allGovernmentsAndAreas.filter { it.parentId != null }

                        val selectedArea =
                            if (userAddress.areaId == null) null
                            else areas.find { it.id == userAddress.areaId }

                        val selectedGovernment =
                            if (selectedArea == null) null
                            else governments.find { it.id == selectedArea.parentId }

                        val userLatLng =
                            if (userAddress.latitude?.toDoubleOrNull() != null && userAddress.longitude?.toDoubleOrNull() != null)
                                LatLng(
                                    userAddress.latitude!!.toDouble(),
                                    userAddress.longitude!!.toDouble()
                                )
                            else
                                MapLatLngConstants.KUWAIT_CITY_LAT_LAN

                        setState {
                            copy(
                                userAddress = userAddress,
                                isEditAddress = userAddress.id != null,
                                userLatLng = userLatLng,
                                allGovernmentsAndAreas = allGovernmentsAndAreas,
                                governments = governments,
                                areas = areas,
                                selectedGovernment = selectedGovernment,
                                selectedArea = selectedArea,
                                streetName = userAddress.streetName,
                                block = userAddress.block,
                                jada = userAddress.addressDescription,
                                houseNumber = userAddress.houseNumber,
                                flatNumber = userAddress.flatNumber,
                                notes = userAddress.addressNotes,
                                toggleEnabled = userAddress.isDefault == true
                            )
                        }
                    }
                }
            }
    })

    private fun addOrEditAddress() = executeCatching({
        val userId = prefersManager.getUserData()?.id
        val userAddress = currentState.userAddress.copy(
            id = currentState.userAddress.id,
            userId = currentState.userAddress.userId ?: userId,
            areaId = currentState.selectedArea?.id,
            streetName = currentState.streetName,
            block = currentState.block,
            houseNumber = currentState.houseNumber,
            flatNumber = currentState.flatNumber,
            addressDescription = currentState.jada,
            addressNotes = currentState.notes,
            isDefault = currentState.toggleEnabled,
            latitude = currentState.userAddress.latitude,
            longitude = currentState.userAddress.longitude,
            isDeleted = false,
        )

        val flowTarget =
            if (currentState.isEditAddress) homeUseCase.updateUserAddress(userAddress)
            else homeUseCase.addUserAddress(userAddress)

        flowTarget.collect { response ->
            when (response) {
                is Result.Error -> globalState.error(response.message.orEmpty())
                is Result.Loading -> globalState.loading(true)
                is Result.Success -> {
                    if (response.code == 0) {
                        setEffect { AddressContract.Effect.Navigation.ReturnToAddressBook }
                    } else {
                        globalState.error(response.message.orEmpty())
                    }
                }
            }
        }
    })

}