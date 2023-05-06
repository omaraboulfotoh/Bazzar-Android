package com.bazzar.android.presentation.cartScreen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.model.home.Product
import com.bazzar.android.R
import com.bazzar.android.common.nullIfEmpty
import com.bazzar.android.presentation.composables.RemoteImageCard
import com.bazzar.android.presentation.theme.BazzarTheme


@Preview
@Composable
fun ProductCartItem(product: Product? = null, productCounter: Int? = 0) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(145.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White),
    ) {
        Row(
            modifier = Modifier.padding(BazzarTheme.spacing.s),
            horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                RemoteImageCard(
                    imageUrl = product?.imagePath,
                    modifier = Modifier.size(88.dp),
                    withShimmer = true
                )
                Row(
                    Modifier.width(73.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_minus),
                        contentDescription = null
                    )
                    Text(
                        text = productCounter.toString(),
                        style = MaterialTheme.typography.overline.copy(
                            color = colorResource(id = R.color.prussian_blue),
                            fontFamily = FontFamily(Font(R.font.montserrat_bold))
                        )
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_plus),
                        modifier = Modifier,
                        contentDescription = null
                    )

                }
            }

            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xxs)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    text = product?.brandTitle.orEmpty(),
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_bold))
                    )
                )

                Text(
                    text = product?.title.orEmpty(),
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_regular))
                    ),
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                                fontSize = 10.sp
                            )
                        ) {
                            append(stringResource(R.string.color))
                        }
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                                fontSize = 14.sp
                            )
                        ) {
                            append(product?.selectedItemDetails?.colorTitle.orEmpty())
                        }
                    }, modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                                fontSize = 10.sp
                            )
                        ) {
                            append(stringResource(R.string.size))
                        }
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                                fontSize = 14.sp
                            )
                        ) {
                            append(product?.selectedItemDetails?.sizeTitle.orEmpty())
                        }
                    }, style = MaterialTheme.typography.subtitle2.copy(

                    ), modifier = Modifier.fillMaxWidth()
                )

            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Image(
                    modifier = Modifier.padding(BazzarTheme.spacing.xxs),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_trash),
                    contentDescription = null
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                            )
                        ) {
                            append(
                                product?.price.toString().nullIfEmpty()
                                    ?: stringResource(R.string.zero_price)
                            )
                        }
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                            )
                        ) {
                            append(stringResource(R.string.home_screen_product_price))
                        }
                    },
                    style = MaterialTheme.typography.subtitle2.copy(

                    ),
                )
            }
        }
    }
}
