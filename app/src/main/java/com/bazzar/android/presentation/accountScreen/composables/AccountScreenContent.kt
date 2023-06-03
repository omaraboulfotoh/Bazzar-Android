package com.bazzar.android.presentation.accountScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.CustomButton
import com.bazzar.android.presentation.composables.EmptyMbcAppBar
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.otpScreen.composables.HeaderTitleBack
import com.bazzar.android.presentation.productDetail.ProductDetailContract
import com.bazzar.android.presentation.productDetail.composables.TalkToUs
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun AccountScreenContent(
    state: AccountContract.State,
    onSendEvent: (AccountContract.Event) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = BottomNavigationHeight)
            .background(BazzarTheme.colors.backgroundColor)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmptyMbcAppBar(title = stringResource(id = R.string.my_account))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = BazzarTheme.spacing.m),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
            ) {
                item {
                    Spacer(modifier = Modifier.height(BazzarTheme.spacing.s))
                }
                item {
                    if (state.isUserLoggedIn) {
                        Spacer(modifier = Modifier.height(BazzarTheme.spacing.l))
                        AccountItem(
                            modifier = Modifier,
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
                            modifier = Modifier,
                            onClick = { onSendEvent(AccountContract.Event.OnSignupClicked) }
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(1.dp))
                }
                item {
                    LanguageItem(
                        modifier = Modifier
                            .clickable {
                                onSendEvent(AccountContract.Event.OnLanguageClicked)
                            }
                    )
                }
                if (state.isUserLoggedIn.not() && state.showWishList) {
                    item {
                        BarItem(
                            modifier = Modifier,
                            title = stringResource(id = R.string.wish_list),
                            iconPainter = painterResource(id = R.drawable.ic_fav),
                            onClick = { onSendEvent(AccountContract.Event.OnWishListClicked) }
                        )
                    }
                }
                if (state.isUserLoggedIn) {
                    item {
                        BarItem(
                            modifier = Modifier,
                            title = stringResource(id = R.string.history_list),
                            iconPainter = painterResource(id = R.drawable.ic_order_history),
                            onClick = { onSendEvent(AccountContract.Event.OnOrderHistoryClicked) }
                        )
                    }
                    item {
                        BarItem(
                            modifier = Modifier,
                            title = stringResource(id = R.string.address_book),
                            iconPainter = painterResource(id = R.drawable.ic_location),
                            onClick = { onSendEvent(AccountContract.Event.OnAddressesClicked) }
                        )
                    }
                    item {
                        BarItem(
                            modifier = Modifier,
                            title = stringResource(id = R.string.wish_list),
                            iconPainter = painterResource(id = R.drawable.ic_fav),
                            onClick = { onSendEvent(AccountContract.Event.OnWishListClicked) }
                        )
                    }
                }
                item {
                    BarItem(
                        modifier = Modifier,
                        title = stringResource(id = R.string.bazar_terms_conditions),
                        iconPainter = painterResource(id = R.drawable.ic_terms_conditions),
                        onClick = { onSendEvent(AccountContract.Event.OnBazzarTermsAndConditionsClicked) }
                    )
                }
                item {
                    BarItem(
                        modifier = Modifier,
                        title = stringResource(id = R.string.about_us),
                        iconPainter = painterResource(id = R.drawable.ic_about_us),
                        onClick = { onSendEvent(AccountContract.Event.OnAboutUsClicked) }
                    )

                }
                item {
                    BarItem(
                        modifier = Modifier,
                        title = stringResource(id = R.string.contact_us),
                        iconPainter = painterResource(id = R.drawable.ic_contact_us),
                        onClick = { onSendEvent(AccountContract.Event.OnContactUsClicked) }
                    )

                }
                item {
                    BarItem(
                        modifier = Modifier,
                        title = stringResource(id = R.string.marketer_submition_form),
                        iconPainter = painterResource(id = R.drawable.ic_terms_conditions),
                        onClick = { onSendEvent(AccountContract.Event.OnMarketerClicked) }
                    )

                }
                item {
                    BarItem(
                        modifier = Modifier,
                        title = stringResource(id = R.string.vendor_submition_form),
                        iconPainter = painterResource(id = R.drawable.ic_terms_conditions),
                        onClick = { onSendEvent(AccountContract.Event.OnVendorClicked) }
                    )

                }
                item {
                    Spacer(modifier = Modifier.height(BazzarTheme.spacing.m))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs)
                    ) {
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs)
                        ) {
                            Icon(
                                modifier = Modifier.clickable { onSendEvent(AccountContract.Event.OnTwitterClicked) },
                                painter = painterResource(id = R.drawable.twittericon),
                                tint = BazzarTheme.colors.primaryButtonColor,
                                contentDescription = ""
                            )
                            Icon(
                                modifier = Modifier.clickable { onSendEvent(AccountContract.Event.OnSnapchatClicked) },
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
                        Text(
                            modifier = Modifier,
                            text = "Version ${BuildConfig.VERSION_NAME}",
                            style = BazzarTheme.typography.body2Medium,
                            color = BazzarTheme.colors.primaryButtonColor,
                        )
                    }
                }
                if (state.isUserLoggedIn) {
                    item {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = BazzarTheme.spacing.m)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs)
                        ) {
                            IconButton(onClick = { onSendEvent(AccountContract.Event.OnLogOutClicked) }) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.Logout,
                                        tint = BazzarTheme.colors.discountText,
                                        contentDescription = ""
                                    )
                                    Text(
                                        modifier = Modifier.padding(start = BazzarTheme.spacing.xxs),
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
                item {
                    Spacer(modifier = Modifier.height(1.dp))
                }
            }
        }
        // talk to us
        TalkToUs(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = BazzarTheme.spacing.m, bottom = BazzarTheme.spacing.xl),
            onClick = { onSendEvent(AccountContract.Event.OnTackToUsClicked) }
        )
    }
}