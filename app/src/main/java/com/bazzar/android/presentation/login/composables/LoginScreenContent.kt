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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.ClickableText
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
            onAction = { onSendEvent(LoginContract.Event.OnBackClicked) })

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            InputMobileNumber(
                phone = state.mobileNumber.orEmpty(),
                isArabic = state.isArabic,
                onPhoneChanged = {
                    onSendEvent(LoginContract.Event.OnPhoneChanged(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = BazzarTheme.colors.primaryButtonTextColorDisabled,
                        shape = RoundedCornerShape(32.5.dp),
                    )
                    .padding(vertical = 4.dp),
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.s)
        ) {
            InputPassword(modifier = Modifier
                .fillMaxWidth()
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

            ClickableText(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(end = BazzarTheme.spacing.xs),
                textAlign = TextAlign.End,
                color = BazzarTheme.colors.primaryButtonColor,
                text = stringResource(id = R.string.forget_password),
                onClick = { onSendEvent(LoginContract.Event.OnForgetPassword) })
        }
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









