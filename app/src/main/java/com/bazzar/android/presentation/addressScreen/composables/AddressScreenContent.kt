package com.bazzar.android.presentation.addressScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.addressScreen.AddressContract
import com.bazzar.android.presentation.composables.PrimaryButton
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun AddressScreenContent(
    state: AddressContract.State, onSendEvent: (AddressContract.Event) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
    ) {
        AddressHeader()
        AddressInputViews()
        PrimaryButton(
            text = stringResource(id = R.string.save_address),
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
