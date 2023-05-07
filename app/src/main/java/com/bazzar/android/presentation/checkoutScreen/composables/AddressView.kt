package com.bazzar.android.presentation.checkoutScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.model.home.UserAddress
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.PrimaryButton
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun AddressView(
    selectedAddress: UserAddress?,
    onAddNewAddressClicked: () -> Unit,
    onContinueClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = BazzarTheme.spacing.m),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
    ) {

        selectedAddress?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.default_address),
                    style = MaterialTheme.typography.subtitle2.copy(
                        color = colorResource(id = R.color.prussian_blue),
                        fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    ),
                )
                Box(
                    modifier = Modifier
                        .padding(start = 77.dp)
                        .width(148.dp)
                        .defaultMinSize(minHeight = 32.dp)
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(16.dp))
                        .background(colorResource(id = R.color.prussian_blue))
                        .border(
                            1.dp,
                            shape = RoundedCornerShape(16.dp),
                            color = colorResource(id = R.color.deep_sky_blue)
                        ), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.change_default_address),
                        style = MaterialTheme.typography.overline.copy(
                            fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                            color = colorResource(id = R.color.white),
                            fontWeight = FontWeight(1)
                        ),
                        textAlign = TextAlign.Center
                    )
                }

            }
            AddressItem(
                address = selectedAddress,
                onEditAddressClick = { },
                onSetAsDefaultClick = { },
            )
        }
        PrimaryButton(
            text = stringResource(id = R.string.add_new_address),
            onClick = {onAddNewAddressClicked},
            textColor = colorResource(id = R.color.prussian_blue),
            background = Color.Unspecified,
            modifier = Modifier
                .padding(top = 61.dp)
                .height(65.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.5.dp))
                .border(
                    1.dp,
                    colorResource(id = R.color.prussian_blue),
                    shape = RoundedCornerShape(32.5.dp)
                )
        )
        PrimaryButton(
            text = stringResource(id = R.string.continue_to_pay),
            onClick = { onContinueClicked() },
            textColor = Color.White,
            background = colorResource(id = R.color.prussian_blue),
            modifier = Modifier
                .padding(top = 191.dp)
                .height(65.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.5.dp))
        )

    }
}

