package com.bazzar.android.presentation.checkOutScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bazzar.android.R
import com.bazzar.android.presentation.checkOutScreen.CheckOutContract
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun CheckOutScreenContent(
    state: CheckOutContract.State,
    onSendEvent: (CheckOutContract.Event) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
    ) {
        BazzarAppBar(title = stringResource(id = R.string.shipping_address), onNavigationClick = {
            onSendEvent(CheckOutContract.Event.OnBackClicked)
        })
        AddressView(state.selectedAddress, onContinueClicked = {
            onSendEvent(CheckOutContract.Event.OnContinueClicked)
        }, onAddNewAddressClicked = {
            onSendEvent(CheckOutContract.Event.OnAddNewAddressClicked)
        })
    }
}
