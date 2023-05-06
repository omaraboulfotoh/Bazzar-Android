package com.bazzar.android.presentation.addressBookScreen.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
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
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = BazzarTheme.spacing.m),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        itemsIndexed(addressList) { index: Int, item: UserAddress ->
            AddressItem(
                address = item,
                onSetAsDefaultClick = { onSetAsDefaultClick.invoke(index) },
                onEditAddressClick = { onEditAddressClick.invoke(item) }
            )
        }

    }
}
