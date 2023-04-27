package com.bazzar.android.presentation.addressBookScreen.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.addressBookScreen.AddressBookContract
import com.bazzar.android.presentation.checkOutScreen.composables.AddressItem
import com.bazzar.android.presentation.composables.PrimaryButton
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun AddressBookScreenContent(
    state: AddressBookContract.State, onSendEvent: (AddressBookContract.Event) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
    ) {
        AddressBookHeader()
        AddressList(state.addressList)
        PrimaryButton(
            text = stringResource(id = R.string.add_new_address),
            onClick = { /*TODO*/ },
            textColor = Color.White,
            background = colorResource(id = R.color.prussian_blue),
            modifier = Modifier
                .height(65.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.5.dp))
        )
    }
}


