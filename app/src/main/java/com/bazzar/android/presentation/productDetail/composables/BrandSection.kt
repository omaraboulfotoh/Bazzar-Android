package com.bazzar.android.presentation.productDetail.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.RemoteImage
import com.bazzar.android.presentation.composables.Subtitle
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.theme.Shapes

@Composable
fun BrandSection(
    brandImagePath: String,
    brandName: String,
    productTitle: String,
    oldPrice: String,
    newPrice: String?,
    modifier: Modifier = Modifier,
    onBrandClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .fillMaxWidth()
            .background(color = BazzarTheme.colors.white)
            .padding(BazzarTheme.spacing.m),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // top brand view
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xxs),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RemoteImage(
                imageUrl = brandImagePath,
                background = BazzarTheme.colors.white,
                modifier = Modifier
                    .size(22.dp)
                    .clip(Shapes.medium)
                    .padding(2.dp)
                    .border(1.dp, color = BazzarTheme.colors.stroke),
                contentScale = ContentScale.Fit
            )
            Subtitle(
                modifier = Modifier.wrapContentSize(),
                text = brandName,
                color = BazzarTheme.colors.black,
                style = BazzarTheme.typography.subtitle1Bold
            )
            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { onBrandClicked() },
                text = stringResource(id = R.string.see_more),
                color = BazzarTheme.colors.black,
                style = BazzarTheme.typography.overline.copy(fontWeight = FontWeight.W500)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
            )
        }

        // product title
        Text(
            text = productTitle, style = MaterialTheme.typography.subtitle1.copy(
                fontFamily = FontFamily(Font(R.font.montserrat_semibold))
            )
        )

        // show the price
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.s),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text =
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily =
                            FontFamily(Font(R.font.montserrat_bold))
                        )
                    ) {
                        append(newPrice ?: oldPrice)
                    }
                    withStyle(
                        style = SpanStyle(
                            fontFamily =
                            FontFamily(Font(R.font.montserrat_regular))
                        )
                    ) {
                        append(
                            stringResource(
                                id = R.string.home_screen_product_price
                            )
                        )
                    }
                },
                Modifier,
                style = MaterialTheme.typography.subtitle2.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_semibold))
                )
            )
            if (newPrice.isNullOrEmpty().not())
                Text(
                    text =
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily =
                                FontFamily(Font(R.font.montserrat_bold))
                            )
                        ) {
                            append(oldPrice)
                        }
                        withStyle(
                            style = SpanStyle(
                                fontFamily =
                                FontFamily(Font(R.font.montserrat_regular))
                            )
                        ) {
                            append(
                                stringResource(
                                    id = R.string.home_screen_product_price
                                )
                            )
                        }
                    },
                    Modifier,
                    color = BazzarTheme.colors.textGray,
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_semibold))
                    )
                )


        }
    }
}