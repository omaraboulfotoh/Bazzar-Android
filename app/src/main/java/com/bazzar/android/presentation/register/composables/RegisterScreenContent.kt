package com.bazzar.android.presentation.register.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.register.RegisterContract
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun RegisterScreenContent(
    state: RegisterContract.State, onSendEvent: (RegisterContract.Event) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = BazzarTheme.spacing.m)
            .padding(bottom = BottomNavigationHeight)
            .background(BazzarTheme.colors.backgroundColor),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.s)

    ) {
        item {
            BazzarAppBar(
                onNavigationClick = {
                    onSendEvent(RegisterContract.Event.OnBackClicked)
                },
                title = stringResource(id = R.string.create_account),
            )
        }
        item {
            RegisterDataEntry(
                modifier = Modifier.padding(top = 14.dp),
                fullName = state.fullName.orEmpty(),
                email = state.email.orEmpty(),
                phone = state.phoneNumber.orEmpty(),
                onPhoneChanged = {
                    onSendEvent(RegisterContract.Event.OnPhoneChanged(it))
                },
                onEmailChanged = {
                    onSendEvent(RegisterContract.Event.OnEmailChanged(it))
                },
                onNameChanged = {
                    onSendEvent(RegisterContract.Event.OnNameChanged(it))
                },
                onTermsAndConditionClicked = {
                    onSendEvent(RegisterContract.Event.OnAgreeTermsAndConditions)
                },
                onCreateAccount = { onSendEvent(RegisterContract.Event.OnCreateAccount) })
        }
    }
}

