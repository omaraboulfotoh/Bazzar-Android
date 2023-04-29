package com.bazzar.android.presentation.login.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.login.LoginContract
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun LoginScreenContent(
    state: LoginContract.State,
    onSendEvent: (LoginContract.Event) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(colorResource(id = R.color.white_smoke)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.l)
    ) {

        LoginHeader(modifier = Modifier, onAction = {

        })

        Spacer(modifier = Modifier.height(40.dp))

        InputMobileNumber(modifier = Modifier.padding(top = 80.dp))
        InputPassword(modifier = Modifier.padding(top = 80.dp))
        LoginButton(
            modifier = Modifier.padding(top = 40.dp),
            isEnabled = state.submitButtonEnabled,
            onSubmit = { onSendEvent(LoginContract.Event.OnLogin) })
        ORBar(modifier = Modifier.padding(top = 48.dp))
        CreateNewAccount(modifier = Modifier.padding(top = 16.dp),
            { onSendEvent(LoginContract.Event.OnCreateNewAccount) })
        ContinueAsGuest(modifier = Modifier.padding(top = 24.dp),
            { onSendEvent(LoginContract.Event.OnContinueAsAGuest) })
    }
}









