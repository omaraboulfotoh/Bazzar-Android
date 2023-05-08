package com.bazzar.android.presentation.changePassword.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bazzar.android.R
import com.bazzar.android.presentation.changePassword.ChangePasswordContract.Event
import com.bazzar.android.presentation.changePassword.ChangePasswordContract.State
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun ChangePasswordScreenContent(
    state: State,
    onSendEvent: (Event) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        BazzarAppBar(
            modifier = Modifier.background(BazzarTheme.colors.white),
            title = stringResource(id = R.string.change_password),
            onNavigationClick = { onSendEvent(Event.OnGoBack) }
        )
        ChangePasswordDataEntry(
            currentPassword = state.currentPassword ?: "",
            newPassword = state.newPassword ?: "",
            onCurrentPasswordChange = { onSendEvent(Event.OnCurrentPasswordChanged(it)) },
            onNewPasswordChange = { onSendEvent(Event.OnNewPasswordChanged(it)) },
            onChangePasswordClick = { onSendEvent(Event.OnChangePasswordClicked) }
        )
    }
}