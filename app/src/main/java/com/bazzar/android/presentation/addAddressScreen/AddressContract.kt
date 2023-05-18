package com.bazzar.android.presentation.addAddressScreen

import com.android.model.home.Area
import com.android.model.home.UserAddress
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class AddressContract {

    data class State(
        val userAddress: UserAddress = UserAddress(),
        val allGovernmentsAndAreas: List<Area> = emptyList(),
        val governments: List<Area> = emptyList(),
        val areas: List<Area> = emptyList(),
        val selectedGovernment: Area? = null,
        val selectedArea: Area? = null,
        val streetName: String? = "",
        val jaddah: String? = "",
        val houseNumber: String? = "",
        val flatNumber: String? = "",
        val notes: String? = "",
        val toggleEnabled: Boolean = false,
        val isEditAddress: Boolean = false,
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnGovernmentSelected(val government: Area) : Event()
        data class OnAreaSelected(val area: Area) : Event()
        data class OnStreetNameChanged(val streetName: String) : Event()
        data class OnJaddahChanged(val jaddah: String) : Event()
        data class OnHouseNumberChanged(val houseNumber: String) : Event()
        data class OnFlatNumberChanged(val flatNumber: String) : Event()
        data class OnNotesChanged(val notes: String) : Event()
        data class OnToggleChanged(val togelEnabled: Boolean) : Event()
        object OnSaveButtonClicked : Event()
        object OnBackIconClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object GoToHomeScreen : Navigation()
            object GoToBack : Navigation()
        }
    }

}
