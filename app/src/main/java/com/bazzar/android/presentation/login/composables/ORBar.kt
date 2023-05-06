package com.bazzar.android.presentation.login.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Divider
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
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun ORBar(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        Divider(
            Modifier
                .weight(1f)
                .height(1.dp), color = BazzarTheme.colors.white
        )
        Text(
            text = stringResource(id = R.string.or),
            style = MaterialTheme.typography.subtitle2.copy(
                colorResource(id = R.color.prussian_blue),
                fontFamily = FontFamily(Font(R.font.montserrat_bold))
            ),
        )
        Divider(
            Modifier
                .weight(1f)
                .height(1.dp), color = BazzarTheme.colors.white
        )
    }
}
