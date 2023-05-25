package com.bazzar.android.presentation.addAddressScreen

import com.android.model.home.Area
import com.android.model.home.UserAddress
import com.bazzar.android.presentation.MapLatLngConstants
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState
import com.google.android.gms.maps.model.LatLng

class AddressContract {

    data class State(
        val userAddress: UserAddress = UserAddress(),
        val userLatLng: LatLng = MapLatLngConstants.KUWAIT_CITY_LAT_LAN,
        val allGovernmentsAndAreas: List<Area> = emptyList(),
        val governments: List<Area> = emptyList(),
        val areas: List<Area> = emptyList(),
        val selectedGovernment: Area? = null,
        val selectedArea: Area? = null,
        val streetName: String? = "",
        val block: String? = "",
        val houseNumber: String? = "",
        val flatNumber: String? = "",
        val notes: String? = "",
        val jada: String? = "",
        val toggleEnabled: Boolean = false,
        val isEditAddress: Boolean = false,
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnGovernmentSelected(val government: Area) : Event()
        data class OnAreaSelected(val area: Area) : Event()
        data class OnStreetNameChanged(val streetName: String) : Event()
        data class OnBlockChanged(val block: String) : Event()
        data class OnHouseNumberChanged(val houseNumber: String) : Event()
        data class OnFlatNumberChanged(val flatNumber: String) : Event()
        data class OnNotesChanged(val notes: String) : Event()
        data class OnJadaValueChanged(val jada: String) : Event()
        data class OnToggleChanged(val togelEnabled: Boolean) : Event()
        object OnSaveButtonClicked : Event()
        object OnBackIconClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object ReturnToAddressBook : Navigation()
            object GoToBack : Navigation()
        }
    }

}
