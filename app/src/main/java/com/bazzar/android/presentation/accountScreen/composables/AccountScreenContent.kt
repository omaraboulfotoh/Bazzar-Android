package com.bazzar.android.presentation.accountScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bazzar.android.BuildConfig
import com.bazzar.android.R
import com.bazzar.android.presentation.accountScreen.AccountContract
import com.bazzar.android.presentation.composables.CustomButton
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.otpScreen.composables.HeaderTitleBack
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun AccountScreenContent(
    state: AccountContract.State,
    onSendEvent: (AccountContract.Event) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor)
            .padding(horizontal = BazzarTheme.spacing.m)
            .padding(bottom = BottomNavigationHeight),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            HeaderTitleBack(
                modifier = Modifier.padding(top = 30.dp),
                stringResource(id = R.string.my_account)
            )
        }
        item {
            if (state.isUserLoggedIn) {
                AccountItem(
                    modifier = Modifier.padding(top = 50.dp),
                    userData = state.userData,
                    onAccountItemClick = {
                        state.userData?.let {
                            onSendEvent(AccountContract.Event.OnAccountClicked(it))
                        }
                    }
                )
            } else {
                CustomButton(
                    text = stringResource(id = R.string.sign_up),
                    modifier = Modifier.padding(top = 50.dp),
                    onClick = { onSendEvent(AccountContract.Event.OnSignupClicked) }
                )
            }
        }
        item {
            LanguageItem(
                modifier = Modifier.padding(top = 24.dp)
            )
        }
        if (state.isUserLoggedIn) {
            item {
                BarItem(
                    modifier = Modifier.padding(top = 24.dp),
                    title = stringResource(id = R.string.history_list),
                    iconPainter = painterResource(id = R.drawable.ic_order_history),
                    onClick = { onSendEvent(AccountContract.Event.OnOrderHistoryClicked) }
                )
            }
            item {
                BarItem(
                    modifier = Modifier.padding(top = 24.dp),
                    title = stringResource(id = R.string.address_book),
                    iconPainter = painterResource(id = R.drawable.ic_location),
                    onClick = { onSendEvent(AccountContract.Event.OnAddressesClicked) }
                )
            }
        }
        item {
            BarItem(
                modifier = Modifier.padding(top = 24.dp),
                title = stringResource(id = R.string.bazar_terms_conditions),
                iconPainter = painterResource(id = R.drawable.ic_terms_conditions),
                onClick = { onSendEvent(AccountContract.Event.OnBazzarTermsAndConditionsClicked) }
            )
        }
        item {
            BarItem(
                modifier = Modifier.padding(top = 24.dp),
                title = stringResource(id = R.string.about_us),
                iconPainter = painterResource(id = R.drawable.ic_about_us),
                onClick = { onSendEvent(AccountContract.Event.OnAboutUsClicked) }
            )

        }
        item {
            BarItem(
                modifier = Modifier.padding(top = 24.dp),
                title = stringResource(id = R.string.contact_us),
                iconPainter = painterResource(id = R.drawable.ic_contact_us),
                onClick = { onSendEvent(AccountContract.Event.OnContactUsClicked) }
            )

        }
        item {
            Row(
                modifier = Modifier.padding(top = 56.dp),
                horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs)
            ) {
                Icon(
                    modifier = Modifier.clickable { onSendEvent(AccountContract.Event.OnTwitterClicked) },
                    painter = painterResource(id = R.drawable.twittericon),
                    tint = BazzarTheme.colors.primaryButtonColor,
                    contentDescription = ""
                )
                Icon(
                    painter = painterResource(id = R.drawable.snapicon),
                    tint = BazzarTheme.colors.primaryButtonColor,
                    contentDescription = ""
                )
                Icon(
                    modifier = Modifier.clickable { onSendEvent(AccountContract.Event.OnFacebookClicked) },
                    painter = painterResource(id = R.drawable.facebookicon),
                    tint = BazzarTheme.colors.primaryButtonColor,
                    contentDescription = ""
                )
                Icon(
                    modifier = Modifier.clickable { onSendEvent(AccountContract.Event.OnInstagramClicked) },
                    painter = painterResource(id = R.drawable.instagramicon),
                    tint = BazzarTheme.colors.primaryButtonColor,
                    contentDescription = ""
                )
            }
        }

        item {
            Text(
                modifier = Modifier.padding(top = BazzarTheme.spacing.m),
                text = "Version ${BuildConfig.VERSION_NAME}",
                style = BazzarTheme.typography.body2Medium,
                color = BazzarTheme.colors.primaryButtonColor,
            )
        }

        item {
            if (state.isUserLoggedIn) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = BazzarTheme.spacing.m, vertical = 56.dp)
                        .fillMaxSize()
                ) {
                    IconButton(onClick = { onSendEvent(AccountContract.Event.OnLogOutClicked) }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.Logout,
                                tint = BazzarTheme.colors.discountText,
                                contentDescription = ""
                            )
                            Text(
                                modifier = Modifier.padding(
                                    top = BazzarTheme.spacing.m,
                                    start = BazzarTheme.spacing.xxs,
                                    bottom = BazzarTheme.spacing.m,
                                    end = BazzarTheme.spacing.m
                                ),
                                text = stringResource(id = R.string.logout),
                                style = BazzarTheme.typography.body2Medium.copy(
                                    color = BazzarTheme.colors.discountText,
                                    fontSize = 14.sp
                                )
                            )
                        }
                    }
                    Text(
                        modifier = Modifier
                            .padding(top = BazzarTheme.spacing.xl)
                            .clickable { },
                        text = stringResource(id = R.string.delete_my_account),
                        style = BazzarTheme.typography.body2Medium.copy(
                            color = BazzarTheme.colors.discountText,
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }
    }

}