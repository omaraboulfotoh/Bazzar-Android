package com.bazzar.android.presentation.locationScreen

import android.location.Geocoder
import com.android.model.home.UserAddress
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.common.getFromLatLng
import com.bazzar.android.presentation.MapLatLngConstants
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.locationScreen.LocationContract.Effect
import com.bazzar.android.presentation.locationScreen.LocationContract.Event
import com.bazzar.android.presentation.locationScreen.LocationContract.State
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    iGlobalState: IGlobalState,
    private val geocoder: Geocoder,
    private val homeUseCase: HomeUseCase
) : BaseViewModel<Event, State, Effect>(iGlobalState) {
    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.OnConfirmLocationClicked -> getAddressFromMap()

            is Event.OnLatLngChanged -> handleOnLatLngChanged(event.latLng)
            is Event.OnPermissionGranted -> setState { copy(isUserLocationEnabled = true) }
            is Event.OnPermissionDenied -> setState { copy(isUserLocationEnabled = false) }
            is Event.OnColumnScrollingEnabledChanged -> setState { copy(columnScrollingEnabled = event.value) }
            is Event.OnBackClicked -> setEffect { Effect.Navigation.GoBack }
            is Event.OnSelectLocation -> {
                setState { copy(startLatLng = event.latLng) }
                handleOnLatLngChanged(event.latLng)
            }
        }
    }

    private fun getAddressFromMap() = executeCatching({

        homeUseCase.getAddressFromMap(
            currentState.currentLatLng.longitude,
            currentState.currentLatLng.latitude
        ).collect {
            when (it) {
                is Result.Error -> setEffect { Effect.Navigation.GoToAddEditAddress(currentState.userAddress) }
                is Result.Loading -> {}
                is Result.Success -> {
                    setEffect {
                        Effect.Navigation.GoToAddEditAddress(
                            it.data?.copy(id = currentState.userAddress.id)
                                ?: currentState.userAddress
                        )
                    }
                }
            }
        }
    })

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