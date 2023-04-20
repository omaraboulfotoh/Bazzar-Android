package com.bazzar.android.presentation.login_screen.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.bazzar.android.R

@Composable
fun TermsAndConditions(modifier:Modifier) {
    Box(modifier = modifier
        .width(375.dp)
        .height(115.dp)) {
        Text(
            modifier = Modifier.padding(top = 78.dp),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = FontFamily(Font(R.font.montserrat_bold))
                    )
                ) {
                    append(stringResource(id = R.string.bazzar))
                }
                withStyle(
                    style = SpanStyle(
                        fontFamily = FontFamily(Font(R.font.montserrat_regular))
                    )
                ) {
                    append(stringResource(id = R.string.terms_and_condition))
                }
            }, style = MaterialTheme.typography.overline.copy(
                color = colorResource(id = R.color.prussian_blue)
            )
        )
    }
}