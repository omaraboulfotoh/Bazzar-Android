package com.bazzar.android.presentation.otpScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.otpScreen.OtpContract
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun OtpScreenContent(
    state: OtpContract.State, onSendEvent: (OtpContract.Event) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.white),
    ) {
        item {
            BazzarAppBar(
                title = stringResource(id = R.string.mobile_confirm),
                onNavigationClick = { }
            )
        }
        item {
            OtpInteraction(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 28.dp),
                otp = state.otp ?: "",
                onConfirmClicked = {
                    onSendEvent(OtpContract.Event.OnConfirmClicked)
                },
                onOtpTextChanged = { text, finalDigitEntered ->
                    onSendEvent(OtpContract.Event.OnOtpChanged(text))
                },
                onSendAgainClicked = {
                    onSendEvent(OtpContract.Event.OnSendAgain)
                }
            )
        }
    }
}
