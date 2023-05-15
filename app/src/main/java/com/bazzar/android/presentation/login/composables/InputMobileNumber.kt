package com.bazzar.android.presentation.login.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun InputMobileNumber(phone: String = "", onPhoneChanged: (String) -> Unit, modifier: Modifier) {
    TextField(
        modifier = modifier,
        shape = RoundedCornerShape(32.5.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = colorResource(id = R.color.prussian_blue),
            backgroundColor = BazzarTheme.colors.white,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        value = phone, onValueChange = onPhoneChanged,
        placeholder = {
            Text(
                text = stringResource(
                    id = R.string.enter_mobile_number
                ),
                style = MaterialTheme.typography.subtitle1.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                    color = colorResource(id = R.color.prussian_blue)
                )
            )
        },
        singleLine = true,
        maxLines = 1,
        leadingIcon = {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_kuwait),
                    contentDescription = null
                )
                Text(text = "+965", color = BazzarTheme.colors.black)
            }
        }
    )
}