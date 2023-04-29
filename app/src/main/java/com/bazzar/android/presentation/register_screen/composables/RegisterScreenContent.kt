package com.bazzar.android.presentation.register_screen.composables

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
import com.bazzar.android.presentation.register_screen.RegisterContract

@Composable
fun RegisterScreenContent(
    state: RegisterContract.State, onSendEvent: (RegisterContract.Event) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white_smoke)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            RegisterHeader(modifier = Modifier.padding(top = 25.dp))
        }
        item {
            RegisterDataEntry(
                modifier = Modifier.padding(top = 14.dp),
                enteredEmail = "",
                enteredUserName = "",
                onTermsAndConditionClicked = { isClicked: Boolean ->
                    onSendEvent(RegisterContract.Event.OnAgreeTermsAndConditions(isClicked))
                },
                onCreateAccount = { onSendEvent(RegisterContract.Event.OnCreateAccount) })
        }
    }
}

