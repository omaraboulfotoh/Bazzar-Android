package com.bazzar.android.presentation.login.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.login.LoginContract
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun LoginScreenContent(
    state: LoginContract.State,
    onSendEvent: (LoginContract.Event) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor)
            .padding(bottom = BottomNavigationHeight)
            .padding(horizontal = BazzarTheme.spacing.m),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xl)
    ) {

        // login header
        LoginHeader(modifier = Modifier,
            onAction = { onSendEvent(LoginContract.Event.OnContinueAsAGuest) })

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            InputMobileNumber(
                phone = state.mobileNumber.orEmpty(),
                onPhoneChanged = {
                    onSendEvent(LoginContract.Event.OnPhoneChanged(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = BazzarTheme.spacing.xs)
                    .border(
                        width = 1.dp,
                        color = BazzarTheme.colors.primaryButtonTextColorDisabled,
                        shape = RoundedCornerShape(32.5.dp),
                    )
                    .padding(vertical = 4.dp),
            )
        }
        InputPassword(modifier = Modifier
            .fillMaxWidth()
            .padding(top = BazzarTheme.spacing.xs)
            .border(
                width = 1.dp,
                color = BazzarTheme.colors.primaryButtonTextColorDisabled,
                shape = RoundedCornerShape(32.5.dp),
            )
            .padding(vertical = 4.dp),
            password = state.password.orEmpty(),
            onPasswordChanged = {
                onSendEvent(LoginContract.Event.OnPasswordChanged(it))
            })
        LoginButton(onSubmit = { onSendEvent(LoginContract.Event.OnLogin) })

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = BazzarTheme.spacing.s),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
        ) {
            ORBar(modifier = Modifier.fillMaxWidth())
            CreateNewAccount(modifier = Modifier.fillMaxWidth()) { onSendEvent(LoginContract.Event.OnCreateNewAccount) }

            if (state.showGuest) {
                ContinueAsGuest(modifier = Modifier.fillMaxWidth()) { onSendEvent(LoginContract.Event.OnContinueAsAGuest) }
            }
            Divider(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp), color = BazzarTheme.colors.white
            )
            Spacer(modifier = Modifier.weight(1f))
            TermsAndConditions(modifier = Modifier)
        }
    }
}









