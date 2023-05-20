package com.bazzar.android.presentation.locationScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bazzar.android.R
import com.bazzar.android.common.advancedShadow
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.MapInColumn
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.locationScreen.LocationContract.Event
import com.bazzar.android.presentation.locationScreen.LocationContract.State
import com.bazzar.android.presentation.composables.permissionRequester.LocationPermissionRequester
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun LocationScreenContent(
    state: State,
    onSendEvent: (Event) -> Unit
) {

    LocationPermissionRequester(
        onPermissionGranted = {
            onSendEvent(Event.OnPermissionGranted)
        },
        onPermissionDenied = {
            onSendEvent(Event.OnPermissionDenied)
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor)
            .padding(bottom = BottomNavigationHeight)
    ) {
        BazzarAppBar(
            title = stringResource(id = R.string.location),
            backgroundColor = BazzarTheme.colors.white,
            onNavigationClick = { onSendEvent(Event.OnBackClicked) }
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState(), state.columnScrollingEnabled)
        ) {
            MapInColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp),
                isUserLocationEnabled = state.isUserLocationEnabled,
                startLatLng = state.startLatLng,
                searchTerm = state.searchTerm,
                mapToolbarEnabled = false,
                onColumnScrollingEnabledChanged = {
                    onSendEvent(
                        Event.OnColumnScrollingEnabledChanged(
                            it
                        )
                    )
                },
                onSearchTermChanged = { onSendEvent(Event.OnSearchTermChanged(it)) },
                onLatLngChanged = { onSendEvent(Event.OnLatLngChanged(it)) }
            )
            LocationInfo(
                address = state.geoCoderAddress
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = BazzarTheme.spacing.s)
                .padding(bottom = BazzarTheme.spacing.xs)
                .advancedShadow(
                    cornersRadius = 33.dp,
                    shadowBlurRadius = 15.dp,
                    alpha = 0.5f,
                    offsetX = 5.dp,
                    offsetY = 5.dp,
                )
                .clip(RoundedCornerShape(33.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = BazzarTheme.colors.primaryButtonColor
            ),
            onClick = { onSendEvent(Event.OnConfirmLocationClicked) }
        ) {
            Text(
                modifier = Modifier.padding(vertical = BazzarTheme.spacing.m),
                text = stringResource(id = R.string.confirm_Location),
                style = BazzarTheme.typography.body2Bold,
                color = BazzarTheme.colors.white,
                fontSize = 14.sp,
            )
        }
    }
}