package com.bazzar.android.presentation.account_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bazzar.android.R

@Composable
fun LanguageItem(modifier: Modifier, iconPainter: Painter) {
    Box(
        modifier = modifier
            .width(343.dp)
            .height(90.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(colorResource(id = R.color.white)),
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .height(19.dp)
                .padding(start = 24.dp)
        ) {
            Icon(painter = iconPainter, null)
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = stringResource(id = R.string.language),
                style = MaterialTheme.typography.subtitle2.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    color = colorResource(id = R.color.prussian_blue)
                )
            )
            Spacer(Modifier.width(93.dp))
            Text(
                text = stringResource(id = R.string.english),
                style = MaterialTheme.typography.subtitle2.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    color = colorResource(id = R.color.deep_sky_blue)
                )
            )
            Text(
                text = stringResource(id = R.string.arabic),
                style = MaterialTheme.typography.subtitle2.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    color = colorResource(id = R.color.Gray59)
                ),
                modifier = Modifier.padding(start = 16.dp)
            )

        }
    }

}