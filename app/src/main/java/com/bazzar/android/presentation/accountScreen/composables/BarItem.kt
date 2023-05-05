package com.bazzar.android.presentation.accountScreen.composables

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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.COLOR_NAVY

@Composable
fun BarItem(
    modifier: Modifier,
    iconPainter: Painter,
    tintColor: Color = COLOR_NAVY,
    title: String,
    onClick: () -> Unit = { },
) {
    Box(
        modifier = modifier
            .width(343.dp)
            .height(90.dp)
            .clickable { onClick.invoke() }
            .clip(RoundedCornerShape(25.dp))
            .background(colorResource(id = R.color.white)),
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .height(19.dp)
                .padding(start = 24.dp)
        ) {
            Icon(painter = iconPainter, null, tint = tintColor)
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = title,
                style = MaterialTheme.typography.subtitle2.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    color = colorResource(id = R.color.prussian_blue)
                )
            )
        }
    }
}