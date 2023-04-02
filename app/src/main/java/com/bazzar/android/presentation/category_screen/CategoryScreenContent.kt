package com.bazzar.android.presentation.category_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.model.movie.ProductModel
import com.bazzar.android.R
import com.bazzar.android.presentation.home_screen.composables.FooterTabBar

@Preview
@Composable
fun CategoryScreen() {
    var isCategory by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white_smoke)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            BrandCategoryHeader(isCategory)
            ToggleBrandCategory({ isCategory = !isCategory }, isCategory)
            CategoryList(isCategory)
            FooterTabBar()
        }
    }
}


@Composable
fun ToggleBrandCategory(onToggle: (Boolean) -> Unit, isCategory: Boolean) {
    IconButton(
        onClick = {
            onToggle(isCategory)
        }
    ) {
        Box(
            Modifier
                .width(if (isCategory) 104.dp else 84.dp)
                .height(28.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(colorResource(id = R.color.titan_white)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.toggle_circle),
                contentDescription = null, tint = colorResource(id = R.color.deep_sky_blue),
                modifier = if (isCategory) Modifier
                    .align(Alignment.CenterStart)
                    .padding(2.dp) else Modifier
                    .align(Alignment.CenterEnd)
                    .padding(2.dp)
            )
            Box(
                modifier = if (isCategory) {
                    Modifier
                        .size(120.dp)
                        .align(Alignment.Center)
                        .padding(end = 5.dp)
                } else {
                    Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                        .padding(start = 5.dp)
                },
                contentAlignment = if (isCategory) Alignment.CenterEnd else Alignment.CenterStart
            ) {
                Text(
                    text = stringResource(
                        id = if (isCategory) R.string.category_category_toggle_title else R.string.category_brands_toggle_title
                    ),
                    style = MaterialTheme.typography.caption.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                        color = colorResource(id = R.color.prussian_blue),
                    )
                )
            }
        }
    }
}

@Composable
fun BrandCategoryHeader(isCategory: Boolean) {
    Row(
        Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = { /* TODO */ },
            modifier = Modifier.padding(start = 16.dp)
        ) {
            if (!isCategory) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "back"
                )
            }
        }
        Box(
            Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(
                    id = if (!isCategory) R.string.category_category_title else R.string.category_brands_title
                ),
                style = MaterialTheme.typography.subtitle1.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    color = colorResource(id = R.color.prussian_blue),
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        IconButton(
            onClick = { /* TODO */ },
            modifier = Modifier.padding(start = 16.dp)
        ) {
            if (isCategory) {
                Icon(
                    painter = painterResource(R.drawable.search_icon),
                    contentDescription = "Search"
                )
            }
        }
    }
}

@Composable
fun CategoryList(isCategory: Boolean) {
    if(isCategory){
        LazyColumn(
            modifier = Modifier
                .padding(top = 20.dp)
                .height(757.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            val productsList = listOf(
                ProductModel(
                    localPoster = R.drawable.first_bazzar,
                    productTitle = "Product title",
                    brandName = "Brand Name",
                    priceBeforeSale = 000.000
                ),
                ProductModel(
                    localPoster = R.drawable.first_bazzar,
                    productTitle = "Product title",
                    brandName = "Brand Name",
                    priceBeforeSale = 000.000
                ),
                ProductModel(
                    localPoster = R.drawable.first_bazzar,
                    productTitle = "Product title",
                    brandName = "Brand Name",
                    priceBeforeSale = 000.000
                ), ProductModel(
                    localPoster = R.drawable.first_bazzar,
                    productTitle = "Product title",
                    brandName = "Brand Name",
                    priceBeforeSale = 000.000
                ), ProductModel(
                    localPoster = R.drawable.first_bazzar,
                    productTitle = "Product title",
                    brandName = "Brand Name",
                    priceBeforeSale = 000.000
                ), ProductModel(
                    localPoster = R.drawable.first_bazzar,
                    productTitle = "Product title",
                    brandName = "Brand Name",
                    priceBeforeSale = 000.000
                ), ProductModel(
                    localPoster = R.drawable.first_bazzar,
                    productTitle = "Product title",
                    brandName = "Brand Name",
                    priceBeforeSale = 000.000
                )
            )

            items(productsList.size) { index ->
                val product = productsList[index]
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .width(343.dp)
                            .height(115.dp)
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = product.localPoster ?: -1),
                            contentDescription = "Product image",
                            modifier = Modifier
                                .width(160.dp)
                                .height(100.dp)
                                .align(Alignment.CenterVertically)
                                .padding(start = 8.dp)
                        )
                        androidx.compose.material3.Text(
                            text = product?.productTitle ?: "",
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .align(Alignment.CenterVertically),
                            style = MaterialTheme.typography.subtitle2.copy(
                                fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                                color = colorResource(id = R.color.prussian_blue)
                            )
                        )
                        IconButton(
                            onClick = { /* TODO */ },
                            modifier = Modifier.padding(start = 15.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_down_arrow),
                                contentDescription = null,
                                tint = colorResource(id = R.color.prussian_blue)
                            )
                        }

                    }
                }
            }
        }

    }
}