package com.bazzar.android.presentation.login.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bazzar.android.R

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    onSubmit: () -> Unit
) {

    Box(Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .clip(RoundedCornerShape(32.5.dp))
                .background(colorResource(id = R.color.prussian_blue))
                .align(Alignment.Center)
                .clickable {
                    onSubmit()
                }
        ) {
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.subtitle1.copy(
                    fontFamily = FontFamily(Font(R.font.siwa_heavy))
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}