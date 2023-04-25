package com.bazzar.android.presentation.checkOutScreen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.MultipleStylesInText
import com.bazzar.android.presentation.composables.PrimaryButton

@Preview
@Composable
fun AddressView(
    phoneNumber: String = "1010",
    address: String = "mansoura",
    toggleEnabled: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
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
                    .height(32.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(colorResource(id = R.color.prussian_blue))
                    .border(
                        1.dp,
                        shape = RoundedCornerShape(16.dp),
                        color = colorResource(id = R.color.deep_sky_blue)
                    ),
                contentAlignment = Alignment.Center
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
        AddressItem(address, phoneNumber, toggleEnabled)
        PrimaryButton(
            text = stringResource(id = R.string.add_new_address),
            onClick = { /*TODO*/ },
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
            onClick = { /*TODO*/ },
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

