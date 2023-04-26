package com.bazzar.android.presentation.addressBookScreen.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.model.home.UserAddress
import com.bazzar.android.presentation.checkOutScreen.composables.AddressItem

@Composable
fun AddressList(addressList: List<UserAddress>) {
    val scrollState = rememberScrollState()
    LazyColumn(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(addressList) { index: Int, item: UserAddress ->
            AddressItem(
                address = item.addressDescription ?: "",
                phoneNumber = "",
                toggleEnabled = true
            )
        }

    }
}
