package com.bazzar.android.presentation.cartScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bazzar.android.R
import com.bazzar.android.common.toPriceFormat
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun CartSummary(cartCounter: Int? = 0, totalAmount: Double) {
    Box(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = BazzarTheme.spacing.m)
            .defaultMinSize(minHeight = 100.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color.White),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(BazzarTheme.spacing.m),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(), text = stringResource(
                    id = R.string.number_in_cart, cartCounter.toString()
                ), textAlign = TextAlign.Center, style = MaterialTheme.typography.subtitle2.copy(
                    fontFamily = FontFamily(Font(R.font.siwa_regular)),
                    color = colorResource(id = R.color.prussian_blue),
                )
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
            // total values
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
            ) {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(id = R.string.total_your_cart),
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontFamily = FontFamily(Font(R.font.siwa_heavy)),
                        color = colorResource(id = R.color.Cruel_Jewel),
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.wrapContentSize(), text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontFamily(Font(R.font.siwa_heavy)),
                            )
                        ) {
                            append(totalAmount.toPriceFormat())
                        }

                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontFamily(Font(R.font.siwa_regular)),
                                fontSize = 10.sp
                            )
                        ) {
                            append(stringResource(id = R.string.home_screen_product_price))
                        }
                    }, style = MaterialTheme.typography.subtitle2.copy(
                        color = colorResource(id = R.color.Cruel_Jewel),
                    )
                )
            }
        }
    }
}