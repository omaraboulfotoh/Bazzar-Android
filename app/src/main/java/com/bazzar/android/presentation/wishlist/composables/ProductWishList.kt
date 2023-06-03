package com.bazzar.android.presentation.wishlist.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.model.home.Product
import com.bazzar.android.R
import com.bazzar.android.common.nullIfEmpty
import com.bazzar.android.common.orFalse
import com.bazzar.android.presentation.composables.RemoteImageCard
import com.bazzar.android.presentation.productsList.composables.DiscountView
import com.bazzar.android.presentation.productsList.composables.ExclusiveView
import com.bazzar.android.presentation.productsList.composables.SoldOutView
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun ProductWishList(
    product: Product,
    onDeleteClicked: () -> Unit,
    onItemClicked: () -> Unit,
    onAddToCartClicked: () -> Unit,
    modifier: Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(156.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .clickable { onItemClicked() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(BazzarTheme.spacing.s),
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs)
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Box(modifier = Modifier.size(88.dp)) {
                        RemoteImageCard(
                            imageUrl = product.imagePath,
                            modifier = Modifier.fillMaxSize(),
                            withShimmer = false
                        )
                        if (product.isSoldOut.orFalse()) {
                            SoldOutView(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .align(Alignment.Center)
                                    .height(24.dp)
                                    .padding(horizontal = 2.dp),
                                BazzarTheme.typography.body2Bold.copy(fontSize = 12.sp)
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        text = product.brandTitle.orEmpty(),
                        style = MaterialTheme.typography.subtitle2.copy(
                            fontFamily = FontFamily(Font(R.font.siwa_heavy))
                        )
                    )

                    Text(
                        text = product.title.orEmpty(),
                        style = MaterialTheme.typography.subtitle2.copy(
                            fontFamily = FontFamily(Font(R.font.siwa_regular))
                        ),
                        maxLines = 3,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End
                ) {
                    Column(
                        modifier = Modifier.wrapContentSize()
                    ) {

                        // check for discount
                        product.discountPercentage?.let {
                            DiscountView(
                                modifier = Modifier.size(width = 70.dp, height = 24.dp), it
                            )
                        }
                        // check for exclusive
                        if (product.isExclusive.orFalse()) ExclusiveView(
                            modifier = Modifier.size(
                                width = 70.dp, height = 24.dp
                            )
                        )
                    }
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = FontFamily(Font(R.font.siwa_heavy)),
                                )
                            ) {
                                append(
                                    product.price.toString().nullIfEmpty()
                                        ?: stringResource(R.string.zero_price)
                                )
                                append(" ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = FontFamily(Font(R.font.siwa_regular)),
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
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp), color = BazzarTheme.colors.stroke
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (product.isSoldOut.orFalse().not()) {
                    Box(modifier = Modifier
                        .size(24.dp)
                        .clickable { onAddToCartClicked() }) {
                        Image(
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.Center),
                            contentScale = ContentScale.FillBounds,
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_cart),
                            contentDescription = "ic_cart"
                        )
                    }
                    Spacer(modifier = Modifier.width(BazzarTheme.spacing.xs))
                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .padding(vertical = BazzarTheme.spacing.xxs)
                    )
                }
                Spacer(modifier = Modifier.width(BazzarTheme.spacing.xs))
                Box(modifier = Modifier
                    .size(24.dp)
                    .clickable { onDeleteClicked() }) {
                    Image(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.Center),
                        contentScale = ContentScale.Fit,
                        imageVector = ImageVector.vectorResource(R.drawable.ic_trash),
                        contentDescription = null
                    )
                }
            }

        }

    }
}

@Composable
fun VerticalDivider(
    color: Color = BazzarTheme.colors.stroke, modifier: Modifier = Modifier
) {
    Surface(
        color = color,
        modifier = modifier
            .fillMaxWidth()
            .height(BazzarTheme.spacing.unit),
    ) {}
}

