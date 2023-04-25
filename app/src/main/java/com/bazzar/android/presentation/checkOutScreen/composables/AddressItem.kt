package com.bazzar.android.presentation.checkOutScreen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.MultipleStylesInText

@Composable
fun AddressItem(address: String, phoneNumber: String, toggleEnabled: Boolean) {
    Box(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(167.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.home),
                    style = MaterialTheme.typography.subtitle2.copy(
                        color = colorResource(id = R.color.black),
                        fontFamily = FontFamily(Font(R.font.montserrat_bold))
                    )
                )
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.icon_awesome_pen),
                    null,
                    modifier = Modifier.padding(start = 228.dp)
                )
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_trash),
                    null,
                    modifier = Modifier.padding(start = 16.dp)
                )

            }
            Text(
                text = address, style = MaterialTheme.typography.subtitle2.copy(
                    color = colorResource(id = R.color.black),
                    fontFamily = FontFamily(Font(R.font.montserrat_bold))
                )
            )
            MultipleStylesInText(stringResource(id = R.string.mobileNumber), phoneNumber)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.set_default_address),
                    style = MaterialTheme.typography.subtitle2.copy(
                        color = colorResource(id = R.color.black),
                        fontFamily = FontFamily(Font(R.font.montserrat_bold))
                    )
                )
                Box(
                    Modifier
                        .padding(start = 96.dp)
                        .width(40.dp)
                        .height(20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colorResource(id = if (toggleEnabled) R.color.deep_sky_blue else R.color.light_gray))
                        .clickable {/* TODO */ }, contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.toggle_circle),
                        contentDescription = null,
                        tint = colorResource(id = if (toggleEnabled) R.color.prussian_blue else R.color.dark_gray),
                        modifier = if (!toggleEnabled) Modifier
                            .align(Alignment.CenterStart)
                            .padding(2.dp) else Modifier
                            .align(Alignment.CenterEnd)
                            .padding(2.dp)
                    )
                }
            }
        }
    }
}