package com.bazzar.android.presentation.productList.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.MessageBody
import com.bazzar.android.presentation.composables.MultipleStylesInText
import com.bazzar.android.presentation.theme.BazzarTheme


@Composable
fun NewBadgeView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_new_bg),
            contentDescription = "new badge"
        )
        MessageBody(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            text = stringResource(id = R.string.new_text),
            color = BazzarTheme.colors.white,
            textAlign = TextAlign.Center,
            style = BazzarTheme.typography.body2Bold.copy(fontSize = 7.sp)
        )
    }
}

@Composable
fun DiscountView(modifier: Modifier = Modifier, discount: Double) {
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_discount_bg),
            contentDescription = "new badge"
        )
        MultipleStylesInText(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            text1 = stringResource(id = R.string.discount_format, discount),
            text2 = stringResource(id = R.string.off_text)
        )
    }
}

@Composable
fun ExclusiveView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_exclusive_bg),
            contentDescription = "new badge"
        )
        MessageBody(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            text = stringResource(id = R.string.exclusive),
            color = BazzarTheme.colors.white,
            textAlign = TextAlign.Center,
            style = BazzarTheme.typography.body2Bold.copy(fontSize = 10.sp)
        )
    }
}

@Composable
fun SoldOutView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_sold_out_bg),
            contentDescription = "new badge"
        )
        MessageBody(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            text = stringResource(id = R.string.soldout),
            color = BazzarTheme.colors.white,
            textAlign = TextAlign.Center,
            style = BazzarTheme.typography.body2Bold.copy(fontSize = 14.sp)
        )
    }
}
