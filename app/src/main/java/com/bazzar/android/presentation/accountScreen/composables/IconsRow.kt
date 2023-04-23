package com.bazzar.android.presentation.accountScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R

@Composable
fun IconsRow(iconPainterList: List<Int>, modifier: Modifier) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier) {
        for (element in iconPainterList) {
            Icon(
                painter = painterResource(element),
                null,
                tint = colorResource(id = R.color.prussian_blue)
            )
        }
    }
}