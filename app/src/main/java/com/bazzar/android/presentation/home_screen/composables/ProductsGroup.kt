package com.bazzar.android.presentation.home_screen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.android.model.home.Product
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.HeaderTextWithViewAll
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductsGroup(productsList: List<Product>?) {
    HeaderTextWithViewAll(text = stringResource(id = R.string.home_screen_products_group))
    LazyRow(
        modifier = Modifier
            .padding(top = 16.dp)
            .wrapContentWidth()
            .height(322.dp),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        contentPadding = PaddingValues(horizontal = 7.dp),
    ) {
        productsList?.let {
            items(it) { product ->
                Box(
                    modifier = Modifier
                        .width(168.dp)
                        .clip(RoundedCornerShape(size = 20.dp))
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .width(168.dp)
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.heart_ic),
                            contentDescription = "favourite image",
                            modifier = Modifier
                                .offset(y = 16.dp)
                                .padding(start = 140.dp)
                                .padding(end = 12.dp)
                        )
                        GlideImage(
                            model = product.imagePath,
                            contentScale = ContentScale.Crop,
                            contentDescription = "Product image",
                            modifier = Modifier
                                .size(152.dp)
                                .padding(top = 40.dp)
                        )
                        Text(
                            text = product.title ?: "",
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .padding(start = 8.dp)
                                .align(Alignment.Start),
                            style = MaterialTheme.typography.subtitle2.copy(
                                fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                                color = colorResource(id = R.color.black)
                            )
                        )
                        Text(
                            text = product.brandTitle ?: "",
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .padding(start = 8.dp)
                                .align(Alignment.Start),
                            style = MaterialTheme.typography.subtitle2.copy(
                                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                                color = colorResource(id = R.color.black)
                            )
                        )
                        Text(
                            modifier = Modifier
                                .paddingFromBaseline(top = 24.dp)
                                .padding(start = 8.dp)
                                .padding(bottom = 32.dp)
                                .align(Alignment.Start),
                            text =
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontFamily =
                                        FontFamily(Font(R.font.montserrat_bold))
                                    )
                                ) {
                                    append(product.oldPrice.toString())
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
                            style = MaterialTheme.typography.subtitle2.copy(
                                color = colorResource(id = R.color.black)
                            )
                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.new_icon),
                        contentDescription = "new_icon",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(y = (-122).dp)
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_cart),
                        contentDescription = "cart_icon",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(y = (-30).dp)
                            .padding(end = 8.dp)
                    )
                }
            }
        }
    }
}
