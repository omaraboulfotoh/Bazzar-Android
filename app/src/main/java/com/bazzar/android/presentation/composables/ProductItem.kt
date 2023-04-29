package com.bazzar.android.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.android.model.home.Product
import com.bazzar.android.R
import com.bazzar.android.common.orFalse
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.productsList.composables.DiscountView
import com.bazzar.android.presentation.productsList.composables.ExclusiveView
import com.bazzar.android.presentation.productsList.composables.NewBadgeView
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.theme.Shapes

@Composable
fun ProductItem(product: Product, onItemClicked: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .height(334.dp)
            .width(164.dp)
            .clickable { onItemClicked(product.id.orZero()) },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.s)
        ) {

            // top image and badges
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.5f)
            ) {
                RemoteImage(
                    imageUrl = product.imagePath,
                    contentScale = ContentScale.Crop,
                    background = BazzarTheme.colors.white,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .clip(Shapes.large)
                )
                // new badge
                if (product.isNew.orFalse())
                    NewBadgeView(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(28.dp)
                    )

                // draw the item for discount and exclusive if exist
                Column(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                ) {

                    // check for discount
                    product.discountPercentage?.let {
                        DiscountView(modifier = Modifier.size(width = 70.dp, height = 24.dp), it)
                    }
                    // check for exclusive
                    if (product.isExclusive.orFalse())
                        ExclusiveView(modifier = Modifier.size(width = 70.dp, height = 24.dp))
                }
                // todo check if sold-out
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = BazzarTheme.spacing.xs),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xxs)
            ) {

                // title
                SectionTitle(
                    text = product.title.orEmpty(),
                    maxLines = 2,
                    modifier = Modifier.align(Alignment.Start),
                )
                // brand title
                SectionTitle(
                    text = product.brandTitle.orEmpty(),
                    maxLines = 1,
                    modifier = Modifier
                        .align(Alignment.Start),
                    style = BazzarTheme.typography.body2
                )

                // the space from title to bottom price
                Spacer(modifier = Modifier.weight(1f))


                // price view
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text =
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(fontFamily = FontFamily(Font(R.font.montserrat_bold)))
                        ) {
                            append(
                                if (product.discountPercentage.orZero() > 0) {
                                    product.oldPrice.toString()
                                } else product.price.toString()
                            )
                        }
                        withStyle(
                            style = SpanStyle(fontFamily = FontFamily(Font(R.font.montserrat_regular)))
                        ) {
                            append(stringResource(id = R.string.home_screen_product_price))
                        }
                    },
                    color = if (product.discountPercentage.orZero() > 0) {
                        BazzarTheme.colors.stroke
                    } else {
                        BazzarTheme.colors.black
                    },
                    style = BazzarTheme.typography.body2Bold
                )

                // if have discount should show the new price
                if (product.discountPercentage.orZero() > 0) {
                    Text(
                        modifier = Modifier.align(Alignment.Start),
                        text =
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(fontFamily = FontFamily(Font(R.font.montserrat_bold)))
                            ) {
                                append(product.price.toString())
                            }
                            withStyle(
                                style = SpanStyle(fontFamily = FontFamily(Font(R.font.montserrat_regular)))
                            ) {
                                append(stringResource(id = R.string.home_screen_product_price))
                            }
                        },
                        color = BazzarTheme.colors.discountText,
                        style = BazzarTheme.typography.body2Bold
                    )
                }
                // bottom space
                Spacer(modifier = Modifier.height(BazzarTheme.spacing.s))
            }
        }
    }
}