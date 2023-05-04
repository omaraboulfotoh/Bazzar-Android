package com.bazzar.android.presentation.accountScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.accountScreen.AccountContract
import com.bazzar.android.presentation.composables.CustomButton
import com.bazzar.android.presentation.otp_screen.composables.HeaderTitleBack

@Composable
fun AccountScreenContent(state: AccountContract.State, onSendEvent: (AccountContract.Event) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white_smoke)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            HeaderTitleBack(
                modifier = Modifier.padding(top = 30.dp),
                stringResource(id = R.string.my_account)
            )
        }
        item {
            CustomButton(
                text = stringResource(id = R.string.sign_up),
                modifier = Modifier.padding(top = 50.dp)
            )
        }
        item {
            LanguageItem(
                modifier = Modifier.padding(top = 24.dp),
                iconPainter = painterResource(R.drawable.icon_awesome_flag)
            )
        }
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
                title = stringResource(id = R.string.bazar_terms_conditions),
                iconPainter = painterResource(id = R.drawable.ic_terms_conditions)
            )
        }
        item {
            BarItem(
                modifier = Modifier.padding(top = 24.dp),
                title = stringResource(id = R.string.about_us),
                iconPainter = painterResource(id = R.drawable.ic_about_us)
            )

        }
        item {
            BarItem(
                modifier = Modifier.padding(top = 24.dp),
                title = stringResource(id = R.string.contact_us),
                iconPainter = painterResource(id = R.drawable.ic_contact_us)
            )

        }
        item {
            IconsRow(
                modifier = Modifier.padding(top = 30.dp),
                iconPainterList = listOf(
                    R.drawable.twittericon,
                    R.drawable.snapicon,
                    R.drawable.facebookicon,
                    R.drawable.instagramicon
                )
            )
        }
    }

}