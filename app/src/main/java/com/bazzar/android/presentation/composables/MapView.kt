package com.bazzar.android.presentation.composables

import android.graphics.Point
import android.view.MotionEvent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

private const val USER_LOCATION_ZOOM_LEVEL = 14f

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MapView(
    modifier: Modifier = Modifier,
) {
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, USER_LOCATION_ZOOM_LEVEL)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = singapore),
            title = "Singapore",
            snippet = "Marker in Singapore"
        )
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ServiceAreaMap(
    modifier: Modifier = Modifier,
    isUserLocationEnabled: Boolean,
    mapLocationPoints: List<LatLng> = emptyList(),
    animateMoveToLocation: Boolean = false,
    isDrawingEnabled: Boolean,
    areaPoints: List<LatLng>,
    onTouchMove: (LatLng) -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    // Create the map ui settings
    val uiSettings by remember(key1 = isUserLocationEnabled) {
        mutableStateOf(
            MapUiSettings(
                compassEnabled = false,
                mapToolbarEnabled = false,
                myLocationButtonEnabled = isUserLocationEnabled,
                zoomControlsEnabled = false,
                indoorLevelPickerEnabled = false
            )
        )
    }

    // Create the map properties
    val properties by remember(key1 = isUserLocationEnabled) {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = isUserLocationEnabled
            )
        )
    }

    // Create the camera position
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(29.3117, 47.4818), 8f)
    }

    // Set camera position map location if possible
    if (mapLocationPoints.isNotEmpty()) {

//        cameraPositionState.move(
//            locations = mapLocationPoints,
//            zoomLevel = USER_LOCATION_ZOOM_LEVEL,
//            animate = animateMoveToLocation
//        )
    }

    // Init draw polygon flag
    var isDrawing by remember { mutableStateOf(false) }
    val drawPolygon by remember(
        key1 = areaPoints.size,
        key2 = isDrawing
    ) {
        mutableStateOf(
            areaPoints.isNotEmpty() && isDrawing.not()
        )
    }

    // Create handle touch event func
    fun handleTouchEvent(event: MotionEvent) = when (event.action) {
        MotionEvent.ACTION_DOWN -> {
            isDrawing = true
        }

        MotionEvent.ACTION_MOVE -> {
            cameraPositionState.projection?.fromScreenLocation(
                Point(
                    event.x.toInt(),
                    event.y.toInt()
                )
            )
                ?.let { onTouchMove(it) }
//            cameraPositionState.projection
//                ?.fromTouchEvent(event)
//                ?.let { onTouchMove(it) }
        }

        MotionEvent.ACTION_UP -> {
            isDrawing = false
        }

        else -> {}
    }

    // Render google map
    GoogleMap(
        uiSettings = uiSettings,
        properties = properties,
        cameraPositionState = cameraPositionState,
        contentPadding = contentPadding,
        modifier = modifier.pointerInteropFilter {
            handleTouchEvent(it)
            isDrawingEnabled
        }
    ) {
        // Validate area points must be not null to prevent crashes
        if (areaPoints.isNotEmpty()) {
            // Check to draw polygon or just polyline
            if (drawPolygon) Polygon(
                points = areaPoints,
                fillColor = Color.Transparent,
                strokeColor = Color.Red,
                strokeWidth = with(LocalDensity.current) { 1.dp.toPx() }
            )
            else Polyline(
                points = areaPoints,
                color = Color.Gray,
                width = with(LocalDensity.current) { 2.dp.toPx() }
            )
        }
    }
}

/*
package com.bazzar.android.presentation.composables

import android.util.Log
import android.view.MotionEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.MapLatLngConstants
import com.bazzar.android.presentation.theme.BazzarTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.DragState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MapInColumn(
    modifier: Modifier = Modifier,
    googleMapModifier: Modifier = Modifier,
    isUserLocationEnabled: Boolean,
    compassEnabled: Boolean = false,
    mapToolbarEnabled: Boolean = false,
    zoomControlsEnabled: Boolean = true,
    indoorLevelPickerEnabled: Boolean = false,
    startingZoom: Float = 14f,
    currentLatLang: LatLng = MapLatLngConstants.KUWAIT_CITY_LAT_LAN,
    latLngBoundsForCameraTarget: LatLngBounds = MapLatLngConstants.KUWAIT_LAT_LNG_BOUNDS,
    onColumnScrollingEnabledChanged: (Boolean) -> Unit,
    onLatLngChanged: (latLang: LatLng) -> Unit,
    onMapLoaded: () -> Unit,
) {

    val uiSettings by remember(key1 = isUserLocationEnabled) {
        mutableStateOf(
            MapUiSettings(
                compassEnabled = compassEnabled,
                mapToolbarEnabled = mapToolbarEnabled,
                myLocationButtonEnabled = isUserLocationEnabled,
                zoomControlsEnabled = zoomControlsEnabled,
                indoorLevelPickerEnabled = indoorLevelPickerEnabled
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
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLatLang, startingZoom)
    }

    // Use a LaunchedEffect keyed on the camera moving state to enable column scrolling when the camera stops moving
    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            onColumnScrollingEnabledChanged(true)
        }
    }
    var isMapLoaded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        GoogleMapViewInColumn(
            modifier = Modifier
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
            currentLatLang = currentLatLang,
            onLatLngChanged = onLatLngChanged,
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
                CircularProgressIndicator(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .wrapContentSize()
                )
            }
        }

        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.ic_map_marker),
            tint = Color.Unspecified,
            contentDescription = ""
        )
    }
}

@Composable
private fun GoogleMapViewInColumn(
    modifier: Modifier,
    googleMapModifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    uiSettings: MapUiSettings,
    mapProperties: MapProperties,
    currentLatLang: LatLng,
    onLatLngChanged: (latLang: LatLng) -> Unit,
    onMapLoaded: () -> Unit,
) {
    val markerState = rememberMarkerState()
    LaunchedEffect(currentLatLang) {
        markerState.position = currentLatLang
    }

    LaunchedEffect(markerState.dragState) {
        markerState.dragState.name
        DragState.END
        when (markerState.dragState) {
            DragState.END -> onLatLngChanged(markerState.position)
            else -> {}
        }
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
        uiSettings = uiSettings,
        onMapLoaded = onMapLoaded,
        onMapLongClick = {
            onLatLngChanged.invoke(it)
        },
        onMyLocationClick = {
            onLatLngChanged.invoke(LatLng(it.latitude, it.longitude))
        },
        onMyLocationButtonClick = {
            true
        },
        onPOIClick = {
            onLatLngChanged.invoke(it.latLng)
        }
    ) {
        // Drawing on the map is accomplished with a child-based API
        val markerClick: (Marker) -> Boolean = {
            Log.d("Ahmed", "${it.title} ${it.position} was clicked")
            cameraPositionState.projection?.let { projection ->
                Log.d("Ahmed", "The current projection is: ${projection.visibleRegion}")
            }
            false
        }
        MarkerInfoWindowContent(
            state = markerState,
            onClick = markerClick,
            draggable = true,
        ) {
            Text(it.title ?: "Title", color = Color.Red)
        }


    }
}
 */

