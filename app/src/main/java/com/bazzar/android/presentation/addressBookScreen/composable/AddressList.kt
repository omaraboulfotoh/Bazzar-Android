package com.bazzar.android.presentation.addressBookScreen.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.model.home.UserAddress
import com.bazzar.android.presentation.checkOutScreen.composables.AddressItem
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun AddressList(
    modifier: Modifier = Modifier,
    addressList: List<UserAddress>,
    onSetAsDefaultClick: (index: Int) -> Unit,
    onEditAddressClick: (userAddress: UserAddress) -> Unit,
    onDeleteAddress: (index: Int) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m),
    ) {
        itemsIndexed(addressList) { index: Int, item: UserAddress ->
            AddressItem(
                address = item,
                onSetAsDefaultClick = { onSetAsDefaultClick.invoke(index) },
                onDeleteAddress = { onDeleteAddress.invoke(index) },
                onEditAddressClick = { onEditAddressClick.invoke(item) },
            )
            if (index == addressList.lastIndex) {
                Spacer(modifier = Modifier.height(96.dp))
            }
        }
    }
}
