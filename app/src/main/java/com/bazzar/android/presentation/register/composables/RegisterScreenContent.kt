package com.bazzar.android.presentation.register.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.register.RegisterContract
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun RegisterScreenContent(
    state: RegisterContract.State, onSendEvent: (RegisterContract.Event) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = BottomNavigationHeight)
            .background(BazzarTheme.colors.white),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)

    ) {
        BazzarAppBar(
            title = stringResource(id = R.string.create_account),
            onNavigationClick = {
                onSendEvent(RegisterContract.Event.OnBackClicked)
            },
        )
        RegisterDataEntry(
            modifier = Modifier.fillMaxWidth(),
            fullName = state.fullName.orEmpty(),
            phone = state.phoneNumber.orEmpty(),
            isArabic = state.isArabic,
            isTermsChecked = state.isAgreeTermsAndConditions,
            onPhoneChanged = {
                onSendEvent(RegisterContract.Event.OnPhoneChanged(it))
            },
            onNameChanged = {
                onSendEvent(RegisterContract.Event.OnNameChanged(it))
            },
            onTermsAndConditionClicked = {
                onSendEvent(RegisterContract.Event.OnAgreeTermsAndConditions)
            },
            onCreateAccount = {
                onSendEvent(RegisterContract.Event.OnCreateAccount)
            }
        )
    }
}

