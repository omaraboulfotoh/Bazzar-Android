package com.bazzar.android.presentation.addressBookScreen.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bazzar.android.R
import com.bazzar.android.presentation.addressBookScreen.AddressBookContract
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun AddressBookScreenContent(
    state: AddressBookContract.State,
    onSendEvent: (AddressBookContract.Event) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = BazzarTheme.spacing.m)
            .background(BazzarTheme.colors.backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
    ) {
        BazzarAppBar(
            title = stringResource(id = R.string.address_book),
            isUpperCase = false,
            backgroundColor = BazzarTheme.colors.backgroundColor,
            onNavigationClick = { onSendEvent(AddressBookContract.Event.OnBackIconClicked) }
        )
        AddressList(
            modifier = Modifier.weight(1f),
            addressList = state.addressList,
            onSetAsDefaultClick = { onSendEvent(AddressBookContract.Event.OnSetAsDefaultClicked(it)) },
            onDeleteAddress = { onSendEvent(AddressBookContract.Event.OnDeleteAddressClicked(it)) },
            onEditAddressClick = { onSendEvent(AddressBookContract.Event.OnEditAddressClicked(it)) }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = BottomNavigationHeight)
                .clip(RoundedCornerShape(33.dp)),
            onClick = { onSendEvent(AddressBookContract.Event.OnAddAddressClicked) }
        ) {
            Text(
                modifier = Modifier.padding(vertical = BazzarTheme.spacing.m),
                text = stringResource(id = R.string.add_new_address),
                style = BazzarTheme.typography.body2Bold,
                fontSize = 14.sp,
                color = BazzarTheme.colors.white,
            )
        }
    }
}


