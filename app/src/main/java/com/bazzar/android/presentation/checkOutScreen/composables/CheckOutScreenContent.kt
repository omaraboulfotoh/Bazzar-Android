package com.bazzar.android.presentation.checkOutScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bazzar.android.presentation.checkOutScreen.CheckOutContract
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun CheckOutScreenContent(
    state: CheckOutContract.State, onSendEvent: (CheckOutContract.Event) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
    ) {
        CheckOutHeader()
        AddressView()
    }
}
