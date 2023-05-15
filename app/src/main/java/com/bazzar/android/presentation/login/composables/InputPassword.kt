package com.bazzar.android.presentation.login.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun InputPassword(
    modifier: Modifier,
    onPasswordChanged: (String) -> Unit,
    password: String = ""
) {
    TextField(
        modifier = modifier,
        colors = TextFieldDefaults.textFieldColors(
            textColor = colorResource(id = R.color.prussian_blue),
            backgroundColor = BazzarTheme.colors.white,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        maxLines = 1,
        value = password,
        visualTransformation = PasswordVisualTransformation(),
        onValueChange = onPasswordChanged,
        shape = RoundedCornerShape(32.5.dp),
        placeholder = {
            Text(
                text = stringResource(id = R.string.password),
                style = MaterialTheme.typography.subtitle1.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                    color = colorResource(id = R.color.prussian_blue)
                ),
            )
        },
    )
}