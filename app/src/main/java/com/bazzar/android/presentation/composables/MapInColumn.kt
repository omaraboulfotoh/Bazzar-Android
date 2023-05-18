package com.bazzar.android.presentation.composables

import android.view.MotionEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.MapLatLngConstants
import com.bazzar.android.presentation.theme.BazzarTheme
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MapInColumn(
    modifier: Modifier = Modifier,
    googleMapModifier: Modifier = Modifier,
    startLatLng: LatLng,
    isUserLocationEnabled: Boolean = true,
    scrollGesturesEnabled: Boolean = true,
    zoomGesturesEnabled: Boolean = true,
    rotationGesturesEnabled: Boolean = true,
    tiltGesturesEnabled: Boolean = true,
    compassEnabled: Boolean = false,
    mapToolbarEnabled: Boolean = false,
    zoomControlsEnabled: Boolean = true,
    indoorLevelPickerEnabled: Boolean = false,
    withSearch: Boolean = true,
    searchTerm: String? = null,
    onSearchTermChanged: (String) -> Unit = { },
    startingZoom: Float = 14f,
    latLngBoundsForCameraTarget: LatLngBounds = MapLatLngConstants.KUWAIT_LAT_LNG_BOUNDS,
    onColumnScrollingEnabledChanged: (Boolean) -> Unit = { },
    onLatLngChanged: (latLang: LatLng) -> Unit = { },
    onMapLoaded: () -> Unit = { },
) {

    val uiSettings by remember(key1 = isUserLocationEnabled) {
        mutableStateOf(
            MapUiSettings(
                compassEnabled = compassEnabled,
                mapToolbarEnabled = mapToolbarEnabled,
                myLocationButtonEnabled = isUserLocationEnabled,
                zoomControlsEnabled = zoomControlsEnabled,
                indoorLevelPickerEnabled = indoorLevelPickerEnabled,
                scrollGesturesEnabled = scrollGesturesEnabled,
                zoomGesturesEnabled = zoomGesturesEnabled,
                rotationGesturesEnabled = rotationGesturesEnabled,
                tiltGesturesEnabled = tiltGesturesEnabled,
            )
        )
    }

    val mapProperties by remember(key1 = isUserLocationEnabled) {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = isUserLocationEnabled,
                latLngBoundsForCameraTarget = latLngBoundsForCameraTarget,
                minZoomPreference = 8f,
                mapType = MapType.NORMAL,
            )
        )
    }

    var isMapLoaded by remember { mutableStateOf(false) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startLatLng, startingZoom)
    }

    // Use a LaunchedEffect keyed on the camera moving state to enable column scrolling when the camera stops moving
    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            onColumnScrollingEnabledChanged(true)
            cameraPositionState.projection?.visibleRegion?.latLngBounds?.center?.let {
                onLatLngChanged(it)
            }
        }
    }

    LaunchedEffect(startLatLng) {
        cameraPositionState.move(CameraUpdateFactory.newLatLng(startLatLng))
    }

    Box(modifier = modifier) {
        GoogleMapViewInColumn(
            modifier = googleMapModifier
                .fillMaxSize()
                .pointerInteropFilter(
                    onTouchEvent = {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                onColumnScrollingEnabledChanged(false)
                                false
                            }

                            else -> {
                                true
                            }
                        }
                    }
                ),
            cameraPositionState = cameraPositionState,
            uiSettings = uiSettings,
            mapProperties = mapProperties,
            onMyLocationClicked = onLatLngChanged,
            onMapLoaded = {
                isMapLoaded = true
                onMapLoaded()
            },
        )
        if (!isMapLoaded) {
            AnimatedVisibility(
                modifier = Modifier
                    .fillMaxSize(),
                visible = !isMapLoaded,
                enter = EnterTransition.None,
                exit = fadeOut()
            ) {
                CircularProgressIndicator(modifier = Modifier.wrapContentSize())
            }
        }

        if (withSearch) {
            SearchTextInput(
                modifier = Modifier
                    .padding(horizontal = BazzarTheme.spacing.m)
                    .padding(top = BazzarTheme.spacing.xs)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = BazzarTheme.colors.stroke,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .background(
                        color = BazzarTheme.colors.primaryButtonColor,
                        shape = RoundedCornerShape(24.dp)
                    ),
                hint = stringResource(id = R.string.search_location),
                value = searchTerm ?: "",
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { }),
                childRowModifier = Modifier.padding(vertical = BazzarTheme.spacing.spacerMini),
                onValueChange = onSearchTermChanged,
                textStyle = BazzarTheme.typography.overlineBold.copy(color = BazzarTheme.colors.white),
                cursorColor = SolidColor(BazzarTheme.colors.white),
                leadingIcon = {
                    Icon(
                        modifier = Modifier.padding(horizontal = BazzarTheme.spacing.s),
                        painter = painterResource(id = R.drawable.search_icon),
                        tint = if (searchTerm.isNullOrEmpty()) BazzarTheme.colors.white else Color.Transparent,
                        contentDescription = ""
                    )
                }
            )
        }

        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .size(24.dp),
            painter = painterResource(id = R.drawable.ic_map_marker),
            tint = BazzarTheme.colors.discountText,
            contentDescription = ""
        )
    }
}

@Composable
private fun GoogleMapViewInColumn(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    uiSettings: MapUiSettings,
    mapProperties: MapProperties,
    onMyLocationClicked: (latLang: LatLng) -> Unit,
    onMapLoaded: () -> Unit,
) {
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = uiSettings,
        onMapLoaded = onMapLoaded,
        onMyLocationClick = {
            onMyLocationClicked.invoke(LatLng(it.latitude, it.longitude))
        },
        onMyLocationButtonClick = {
            true
        }
    )
}