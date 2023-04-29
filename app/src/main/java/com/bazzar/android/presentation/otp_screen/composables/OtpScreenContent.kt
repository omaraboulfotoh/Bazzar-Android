package com.bazzar.android.presentation.otp_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.otp_screen.OtpContract

@Composable
fun OtpScreenContent(
    state: OtpContract.State, onSendEvent: (OtpContract.Event) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white_smoke)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            HeaderTitleBack(
                modifier = Modifier.padding(start = 30.dp),
                stringResource(id = R.string.mobile_confirm)
            )
        }
        item {
            OtpInteraction(modifier = Modifier.padding(start = 16.dp))
        }
    }
}
