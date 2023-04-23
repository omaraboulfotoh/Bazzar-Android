package com.bazzar.android.presentation.categoryScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bazzar.android.R

@Composable
fun ToggleBrandCategory(onToggle: () -> Unit, isCategory: Boolean) {
    IconButton(onClick = { onToggle() }) {
        Box(
            Modifier
                .width(if (!isCategory) 104.dp else 84.dp)
                .height(28.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(colorResource(id = R.color.titan_white)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.toggle_circle),
                contentDescription = null,
                tint = colorResource(id = R.color.deep_sky_blue),
                modifier = if (!isCategory) Modifier
                    .align(Alignment.CenterStart)
                    .padding(2.dp) else Modifier
                    .align(Alignment.CenterEnd)
                    .padding(2.dp)
            )
            Box(
                modifier = if (!isCategory) {
                    Modifier
                        .size(120.dp)
                        .align(Alignment.Center)
                        .padding(end = 5.dp)
                } else {
                    Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                        .padding(start = 5.dp)
                },
                contentAlignment = if (!isCategory) Alignment.CenterEnd else Alignment.CenterStart
            ) {
                Text(
                    text = stringResource(
                        id = if (!isCategory) R.string.category_category_toggle_title else R.string.category_brands_toggle_title
                    ), style = MaterialTheme.typography.caption.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                        color = colorResource(id = R.color.prussian_blue),
                    )
                )
            }
        }
    }
}
