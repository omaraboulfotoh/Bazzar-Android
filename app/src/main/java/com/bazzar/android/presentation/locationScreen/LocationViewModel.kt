package com.bazzar.android.presentation.locationScreen

import android.location.Geocoder
import com.android.model.home.UserAddress
import com.bazzar.android.common.getFromLatLng
import com.bazzar.android.presentation.MapLatLngConstants
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.locationScreen.LocationContract.Event
import com.bazzar.android.presentation.locationScreen.LocationContract.State
import com.bazzar.android.presentation.locationScreen.LocationContract.Effect
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    iGlobalState: IGlobalState,
    private val placesClient: PlacesClient,
    private val geocoder: Geocoder,
) : BaseViewModel<Event, State, Effect>(iGlobalState) {
    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.OnConfirmLocationClicked -> {
                setEffect { Effect.Navigation.GoToAddEditAddress(currentState.userAddress) }
            }

            is Event.OnLatLngChanged -> handleOnLatLngChanged(event.latLng)
            is Event.OnPermissionGranted -> setState { copy(isUserLocationEnabled = true) }
            is Event.OnPermissionDenied -> setState { copy(isUserLocationEnabled = false) }
            is Event.OnColumnScrollingEnabledChanged -> setState { copy(columnScrollingEnabled = event.value) }
            is Event.OnSearchTermChanged -> handleOnSearchTermChanged(event.term)
            is Event.OnBackClicked -> setEffect { Effect.Navigation.GoBack }
        }
    }

    private fun handleOnLatLngChanged(latLng: LatLng) = executeCatching({
        setState {
            copy(
                userAddress = currentState.userAddress.copy(
                    latitude = "${latLng.latitude}",
                    longitude = "${latLng.longitude}",
                ),
                currentLatLng = latLng
            )
        }
        geocoder.getFromLatLng(latLng) {
            setState {
                copy(
                    userAddress = currentState.userAddress.copy(
                        streetName = it?.thoroughfare ?: "",
                        latitude = "${latLng.latitude}",
                        longitude = "${latLng.longitude}",
                    ),
                    geoCoderAddress = it
                )
            }
        }
    })

    private fun handleOnSearchTermChanged(term: String) = executeCatching({
        setState { copy(searchTerm = term) }
    })

    fun init(userAddress: UserAddress?) {
        userAddress?.let {
            val currentLatLng =
                if (it.latitude?.toDoubleOrNull() != null || it.longitude?.toDoubleOrNull() != null) {
                    LatLng(it.latitude!!.toDouble(), it.longitude!!.toDouble())
                } else {
                    MapLatLngConstants.KUWAIT_CITY_LAT_LAN
                }

            setState {
                copy(
                    userAddress = it,
                    startLatLng = currentLatLng,
                    currentLatLng = LatLng(startLatLng.latitude, startLatLng.longitude)
                )
            }
        }
    }
}