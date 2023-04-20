package com.bazzar.android.presentation.login_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bazzar.android.R

@Composable
fun ORBar(modifier:Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .height(5.dp)
                .fillMaxWidth()
                .align(Alignment.Center)
        )
        Text(
            text = stringResource(id = R.string.or),
            style = MaterialTheme.typography.subtitle2.copy(
                colorResource(id = R.color.prussian_blue),
                fontFamily = FontFamily(Font(R.font.montserrat_bold))
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
