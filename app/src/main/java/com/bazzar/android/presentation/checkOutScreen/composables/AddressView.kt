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