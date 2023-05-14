package com.bazzar.android.presentation.addAddressScreen

import com.android.local.SharedPrefersManager
import com.android.model.home.Area
import com.android.model.home.UserAddress
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
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
            is AddressContract.Event.OnJaddahChanged -> setState { copy(jaddah = event.jaddah) }
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

    fun init(userAddress: UserAddress?) = executeCatching({
        setState { copy(userAddress = userAddress, isEditAddress = userAddress != null) }
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
                            if(userAddress == null) null
                            else areas.find { it.id == userAddress.areaId }
                        val selectedGovernment =
                            if(selectedArea == null) null
                            else governments.find { it.id == selectedArea.parentId }
                        setState {
                            copy(
                                allGovernmentsAndAreas = allGovernmentsAndAreas,
                                governments = governments,
                                areas = areas,
                                selectedGovernment = selectedGovernment,
                                selectedArea = selectedArea,
                                streetName = userAddress?.streetName,
                                houseNumber = userAddress?.houseNumber,
                                flatNumber = userAddress?.flatNumber,
                                notes = userAddress?.addressNotes,
                                toggleEnabled = userAddress?.isDefault == true
                            )
                        }
                    }
                }
            }
    })

    private fun addOrEditAddress() = executeCatching({
        val userAddress = (currentState.userAddress ?: UserAddress()).copy(
            areaId = currentState.selectedArea?.id,
            streetName = currentState.streetName,
            houseNumber = currentState.houseNumber,
            flatNumber = currentState.flatNumber,
            addressNotes = currentState.notes,
            isDefault = currentState.toggleEnabled,
        )

        val flowTarget =
            if (currentState.isEditAddress) homeUseCase.updateUserAddress(userAddress)
            else homeUseCase.addUserAddress(userAddress)

        flowTarget.collect { response ->
            when (response) {
                is Result.Error -> globalState.error(response.message.orEmpty())
                is Result.Loading -> globalState.loading(true)
                is Result.Success -> { setEffect { AddressContract.Effect.Navigation.GoToHomeScreen } }
            }
        }
    })

}