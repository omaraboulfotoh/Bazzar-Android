package com.bazzar.android.presentation.addAddressScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bazzar.android.R
import com.bazzar.android.presentation.addAddressScreen.AddressContract
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun AddressScreenContent(
    title: String,
    state: AddressContract.State,
    onSendEvent: (AddressContract.Event) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.white),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
    ) {
        BazzarAppBar(
            title = title,
            onNavigationClick = { onSendEvent(AddressContract.Event.OnBackIconClicked) },
            isUpperCase = false,
        )
        AddressInputViews(
            modifier = Modifier.weight(1f),
            governments = state.governments,
            areas = state.areas,
            selectedGovernment = state.selectedGovernment,
            selectedArea = state.selectedArea,
            streetName = state.streetName,
            jaddah = state.jaddah,
            houseNumber = state.houseNumber,
            flatNumber = state.flatNumber,
            notes = state.notes,
            toggleEnabled = state.toggleEnabled,
            onSelectGovernment = { onSendEvent(AddressContract.Event.OnGovernmentSelected(it)) },
            onSelectArea =  { onSendEvent(AddressContract.Event.OnAreaSelected(it)) },
            onStreetNameChangec = { onSendEvent(AddressContract.Event.OnStreetNameChanged(it)) },
            onJaddahChanged = { onSendEvent(AddressContract.Event.OnJaddahChanged(it)) },
            onHouseNumberChanged = { onSendEvent(AddressContract.Event.OnHouseNumberChanged(it)) },
            onFlatNumberChanged = { onSendEvent(AddressContract.Event.OnFlatNumberChanged(it)) },
            onNotesChanged = { onSendEvent(AddressContract.Event.OnNotesChanged(it)) },
            onToggleChanged = { onSendEvent(AddressContract.Event.OnToggleChanged(it)) },
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = BottomNavigationHeight)
                .clip(RoundedCornerShape(33.dp)),
            onClick = { onSendEvent(AddressContract.Event.OnSaveButtonClicked) }
        ) {
            Text(
                modifier = Modifier.padding(vertical = BazzarTheme.spacing.m),
                text = stringResource(id = R.string.save_address),
                style = BazzarTheme.typography.body2Bold,
                fontSize = 14.sp,
                color = BazzarTheme.colors.white,
            )
        }
    }
}
