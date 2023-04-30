package com.bazzar.android.presentation.productDetail.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bazzar.android.R

@Composable
fun BuyItem(modifier: Modifier, onBuyNowClicked: () -> Unit) {
    Box(
        modifier = modifier
            .width(124.dp)
            .height(65.dp)
            .clip(RoundedCornerShape(32.5.dp))
            .background(colorResource(id = R.color.prussian_blue))
            .clickable { onBuyNowClicked() },
        contentAlignment = Alignment.Center
    )
    {
        Text(
            text = stringResource(id = R.string.buy_now),
            style = MaterialTheme.typography.subtitle2.copy(
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.montserrat_bold))
            )
        )
    }
}