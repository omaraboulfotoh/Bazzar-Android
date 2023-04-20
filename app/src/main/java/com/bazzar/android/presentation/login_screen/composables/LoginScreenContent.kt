package com.bazzar.android.presentation.login_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.login_screen.LoginContract

@Composable
fun LoginScreenContent(
    state: LoginContract.State, onSendEvent: (LoginContract.Event) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white_smoke)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            LoginHeader(modifier = Modifier.padding(top = 16.dp))
        }
        item {
            InputMobileNumber(modifier = Modifier.padding(top = 80.dp))
        }
        item {
            InputPassword(modifier = Modifier.padding(top = 80.dp))
        }
        item {
            LoginButton(modifier = Modifier.padding(top = 40.dp))
        }
        item {
            ORBar(modifier = Modifier.padding(top = 48.dp))
        }
        item {
            CreateNewAccount(modifier = Modifier.padding(top = 16.dp))
        }
        item {
            ContinueAsGuest(modifier = Modifier.padding(top = 24.dp))
        }
        item {
            TermsAndConditions(modifier = Modifier.padding(top = 78.dp))
        }
    }
}









