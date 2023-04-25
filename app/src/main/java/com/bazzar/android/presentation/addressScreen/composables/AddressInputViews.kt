package com.bazzar.android.presentation.addressScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.PickerTextField
import com.bazzar.android.presentation.composables.TextInputField

@Preview
@Composable
fun AddressInputViews(toggleEnabled: Boolean = false) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .background(Color.White)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        PickerTextField(
            text = "",
            label = stringResource(R.string.governorate),
            placeholder = stringResource(id = R.string.select_your_governorate),
            onPick = {}
        )
        PickerTextField(
            text = "",
            label = stringResource(R.string.area),
            placeholder = stringResource(id = R.string.select_your_area),
            onPick = {}
        )
        TextInputField(
            text = "",
            label = stringResource(id = R.string.street),
            placeholder = stringResource(id = R.string.select_your_street_name),
            modifier = Modifier
                .wrapContentHeight()
                .defaultMinSize(88.dp, 88.dp)
                .fillMaxWidth(),
            onValueChange = {}
        )
        Text(
            text = stringResource(id = R.string.optional_data),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.subtitle1.copy(
                fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                color = colorResource(id = R.color.prussian_blue),
            )
        )
        TextInputField(
            text = "",
            label = stringResource(id = R.string.jaddah),
            placeholder = stringResource(id = R.string.optional),
            modifier = Modifier
                .wrapContentHeight()
                .defaultMinSize(88.dp, 88.dp)
                .fillMaxWidth(),
            onValueChange = {}

        )
        TextInputField(
            text = "",
            label = stringResource(id = R.string.house_number),
            placeholder = stringResource(id = R.string.optional),
            modifier = Modifier
                .wrapContentHeight()
                .defaultMinSize(88.dp, 88.dp)
                .fillMaxWidth(),
            onValueChange = {}

        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            TextInputField(
                text = "",
                label = stringResource(id = R.string.floor_number),
                placeholder = stringResource(id = R.string.optional),
                modifier = Modifier
                    .width(150.dp)
                    .height(60.dp),
                onValueChange = {}
            )
            TextInputField(
                text = "",
                label = stringResource(id = R.string.flat_number),
                placeholder = stringResource(id = R.string.optional),
                modifier = Modifier
                    .width(150.dp)
                    .height(60.dp),
                onValueChange = {},
            )

        }
        TextInputField(
            text = "",
            label = stringResource(id = R.string.notes),
            placeholder = stringResource(id = R.string.optional),
            modifier = Modifier
                .wrapContentHeight()
                .defaultMinSize(88.dp, 88.dp)
                .fillMaxWidth(),
            onValueChange = {}

        )
        Row {
            Text(
                text = stringResource(id = R.string.set_default_address),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    color = colorResource(id = R.color.prussian_blue),
                )
            )
            Spacer(Modifier.width(103.dp))
            Box(
                Modifier
                    .width(40.dp)
                    .height(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorResource(id = if (toggleEnabled) R.color.deep_sky_blue else R.color.light_gray))
                    .clickable {/* TODO */ },
                contentAlignment = Alignment.Center
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