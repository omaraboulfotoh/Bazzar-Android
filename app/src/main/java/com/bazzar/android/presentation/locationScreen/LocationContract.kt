package com.bazzar.android.presentation.locationScreen

import android.location.Address
import android.location.Location
import com.android.model.home.UserAddress
import com.bazzar.android.presentation.MapLatLngConstants
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState
import com.google.android.gms.maps.model.LatLng

class LocationContract {
    data class State(
        val userAddress: UserAddress = UserAddress(),
        val geoCoderAddress: Address? = null,
        val startLatLng: LatLng = MapLatLngConstants.KUWAIT_CITY_LAT_LAN,
        val currentLatLng: LatLng = MapLatLngConstants.KUWAIT_CITY_LAT_LAN,
        val currentUserLocation: Location? = null,
        val isUserLocationEnabled: Boolean = false,
        val shouldRequestLocationPermissions: Boolean = false,
        val columnScrollingEnabled: Boolean = true,
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnColumnScrollingEnabledChanged(val value: Boolean) : Event()
        data class OnLatLngChanged(val latLng: LatLng) : Event()
        data class OnSelectLocation(val latLng: LatLng) : Event()
        object OnPermissionGranted : Event()
        object OnPermissionDenied : Event()
        object OnConfirmLocationClicked : Event()
        object OnMyLocationButtonClick : Event()
        object OnRequestPermission : Event()
        object OnBackClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToAddEditAddress(val userAddress: UserAddress) : Navigation()
            object GoBack : Navigation()
        }
    }
}