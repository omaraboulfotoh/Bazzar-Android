package com.bazzar.android.presentation.product_detail_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.RemoteImage

@Composable
fun BrandSection(
    brandImagePath: String,
    brandName: String,
    productTitle: String,
    oldPrice: String,
    newPrice: String,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .background(color = Color.White)
    ) {
        Column(
            Modifier.padding(top = 18.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier

                    .fillMaxWidth()
            ) {
                RemoteImage(
                    imageUrl = brandImagePath,
                    modifier = Modifier.size(22.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = brandName,
                    style = androidx.compose.material.MaterialTheme.typography.subtitle1.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_bold))
                    )
                )
                Spacer(modifier = Modifier.width(155.dp))
                Text(
                    text = stringResource(id = R.string.see_more),
                    style = androidx.compose.material.MaterialTheme.typography.subtitle1.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_bold))
                    )
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = null,
                    Modifier.padding(start = 4.dp)
                )
            }
            Text(
                text = productTitle, style = MaterialTheme.typography.subtitle1.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_semibold))
                )
            )
            Row {
                Text(
                    text =
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily =
                                FontFamily(Font(R.font.montserrat_bold))
                            )
                        ) {
                            append(newPrice.toString())
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
                    Modifier.alpha(0.7f),
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_semibold))
                    )
                )
                Text(
                    text =
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily =
                                FontFamily(Font(R.font.montserrat_bold))
                            )
                        ) {
                            append(oldPrice.toString())
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
                    Modifier.alpha(0.7f),
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_semibold))
                    )
                )


            }
        }
    }
}