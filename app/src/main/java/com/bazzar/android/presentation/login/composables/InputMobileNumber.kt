package com.bazzar.android.presentation.login.composables

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bazzar.android.R

@Composable
fun InputMobileNumber(inputText: String = "", modifier: Modifier) {
    var value: String = inputText
    TextField(value = value, onValueChange = { newText -> value = newText }, placeholder = {
        Text(
            text = stringResource(
                id = R.string.enter_mobile_number
            ),
            style = androidx.compose.material.MaterialTheme.typography.subtitle1.copy(
                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                color = colorResource(id = R.color.prussian_blue)
            ),
            modifier = modifier
                .alpha(.5f)
                .width(320.dp)
                .height(60.dp)
        )
    },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_kuwait),
                contentDescription = ""
            )
        })
}