package com.bazzar.android.presentation.cartScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bazzar.android.R

@Composable
fun CartSummary() {
    Box(
        Modifier
            .padding(top = 20.dp)
            .width(343.dp)
            .height(95.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color.White),
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(top = 64.dp),
            text = stringResource(id = R.string.total_your_cart),
            style = MaterialTheme.typography.subtitle2.copy(
                fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                color = colorResource(id = R.color.Cruel_Jewel),
            )
        )
        Text(
            modifier = Modifier
                .padding(start = 68.dp)
                .padding(top = 16.dp),
            text = stringResource(
                id = R.string.number_in_cart,/*state.productCartList.size*/
                50
            ),
            style = MaterialTheme.typography.subtitle2.copy(
                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                color = colorResource(id = R.color.prussian_blue),
            )
        )
        Text(
            modifier = Modifier
                .padding(start = 273.dp)
                .padding(top = 64.dp),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    )
                ) {
                    append(/*state.totalPrice*/"2222.5")
                }

                withStyle(
                    style = SpanStyle(
                        fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                        fontSize = 10.sp
                    )
                ) {
                    append(stringResource(id = R.string.home_screen_product_price))
                }
            },
            style = MaterialTheme.typography.subtitle2.copy(
                color = colorResource(id = R.color.Cruel_Jewel),
            )
        )

    }
}