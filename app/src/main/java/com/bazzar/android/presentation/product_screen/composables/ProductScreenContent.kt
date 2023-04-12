package com.bazzar.android.presentation.product_screen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.model.movie.ProductModel
import com.bazzar.android.R
import com.bazzar.android.presentation.home_screen.composables.FooterTabBar
import com.bazzar.android.presentation.home_screen.composables.Indicator
import com.bazzar.android.presentation.home_screen.composables.MenuBarItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@Preview
@Composable
fun ProductScreen() {
    var searchClicked by remember { mutableStateOf(true) }
    val productsList = mutableListOf<ProductModel>()
    val productFilter = ProductModel(
        localPoster = R.drawable.first_bazzar,
        productTitle = "Product title",
        brandName = "Brand Name",
        priceBeforeSale = 000.000
    )

    for (i in 1..17) {
        val product = ProductModel(
            localPoster = R.drawable.first_bazzar,
            productTitle = "Product title",
            brandName = "Brand Name",
            priceBeforeSale = 000.000
        )
        productsList.add(product)
    }
    val imageList = listOf(
        R.drawable.first_bazzar,
        R.drawable.second_bazzar,
        R.drawable.third_bazzar,
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white_smoke)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            ProductHeader()
            Spacer(Modifier.height(41.dp))
            SearchProduct(searchClicked = searchClicked) {
                searchClicked = !searchClicked
            }
            ProductHorizontalList(imageList)
            Spacer(Modifier.height(10.dp))
            BazarCategoryList()
            Spacer(Modifier.height(10.dp))
            SortFilterBar(productFilter)
            ProductColumnGroup(productsList)
            FooterTabBar(MenuBarItem.Home)
        }
    }

}

@Composable
fun ProductColumnGroup(productsList: List<ProductModel>) {
    Box(
        Modifier
            .height(800.dp)
            .padding(top = 16.dp)
    ) {
        LazyVerticalGrid(GridCells.Fixed(2), contentPadding = PaddingValues(16.dp)) {
            items(productsList.size) { index ->
                val product = productsList[index]
                Box(
                    modifier = Modifier
                        .width(168.dp)
                        .padding(top = 16.dp)
                        .clip(RoundedCornerShape(size = 20.dp))
                ) {
                    Card(modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 30.dp),
                        content = {})

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
                        Image(
                            painter = painterResource(id = product.localPoster ?: -1),
                            contentDescription = "Product image",
                            modifier = Modifier
                                .size(152.dp)
                                .padding(top = 40.dp)
                        )
                        Text(
                            text = product?.productTitle ?: "",
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
                            text = product.brandName ?: "",
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
                                .align(Alignment.Start), text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontFamily = FontFamily(Font(R.font.montserrat_bold))
                                    )
                                ) {
                                    append(product.priceBeforeSale.toString() ?: "")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontFamily = FontFamily(Font(R.font.montserrat_regular))
                                    )
                                ) {
                                    append(
                                        stringResource(
                                            id = R.string.home_screen_product_price
                                        )
                                    )
                                }
                            }, style = MaterialTheme.typography.subtitle2.copy(
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

@Composable
fun SortFilterBar(product: ProductModel) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(42.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.whisper)),
        elevation = CardDefaults.cardElevation(defaultElevation = 30.dp),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.whisper))
            ) {
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp), text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = FontFamily(Font(R.font.montserrat_semibold))
                                )
                            ) {
                                append(product.itemCounter?.toString() ?: "0000")
                                append(
                                    stringResource(id = R.string.product_items_counter)
                                )
                            }
                        }, style = MaterialTheme.typography.overline.copy(
                            color = colorResource(id = R.color.prussian_blue)
                        )
                    )
                    Spacer(modifier = Modifier.width(152.dp))
                    Icon(
                        painterResource(id = R.drawable.ic_sort),
                        contentDescription = null,
                        tint = colorResource(id = R.color.prussian_blue)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.sort),
                        style = MaterialTheme.typography.caption.copy(
                            fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                            color = colorResource(id = R.color.prussian_blue)
                        )
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        painterResource(id = R.drawable.ic_filter),
                        contentDescription = null,
                        tint = colorResource(id = R.color.prussian_blue)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.filter),
                        style = MaterialTheme.typography.caption.copy(
                            fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                            color = colorResource(id = R.color.prussian_blue)
                        )
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(RoundedCornerShape(7.dp))
                            .background(colorResource(id = R.color.prussian_blue))
                    ) {
                        Text(
                            text = product.filterNumSelected?.toString() ?: "2",
                            modifier = Modifier
                                .align(Alignment.Center),
                            style = MaterialTheme.typography.overline.copy(
                                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                                color = colorResource(id = R.color.white)
                            )
                        )

                    }
                }
            }
        })

}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductHorizontalList(imageList: List<Int>) {


    val pagerState = rememberPagerState(
        initialPage = 0,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(171.dp)
            .padding(top = 16.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            count = imageList.size,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(4.dp)

        ) { page ->
            Image(
                painter = painterResource(id = imageList[page]),
                modifier = Modifier
                    .width(335.dp)
                    .wrapContentHeight()
                    .padding(horizontal = 4.dp),
                contentDescription = null,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in imageList.indices) {
                Indicator(selected = i == pagerState.currentPage) {}
            }
        }
    }
}

@Composable
fun SearchProduct(searchClicked: Boolean, onSearchClick: (Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .width(343.dp)
            .height(35.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(colorResource(id = if (searchClicked) R.color.white_smoke else R.color.white))
            .border(
                1.dp,
                colorResource(id = if (searchClicked) R.color.prussian_blue else R.color.white_smoke),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        IconButton(
            onClick = { onSearchClick(searchClicked) },
        ) {
            Row(
                modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .weight(1f), contentAlignment = Alignment.Center
                ) {
                    if (!searchClicked) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 64.dp),
                            painter = painterResource(id = R.drawable.search_icon),
                            contentDescription = null,
                            tint = colorResource(id = R.color.prussian_blue)
                        )
                        androidx.compose.material.Text(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 89.dp),
                            text = stringResource(id = R.string.product_search_title),
                            style = MaterialTheme.typography.overline.copy(
                                fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                                color = colorResource(id = R.color.prussian_blue).copy(alpha = 0.4f),
                            ),
                        )
                    } else {
                        var searchText by remember { mutableStateOf("") }
                        TextField(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .width(280.dp)
                                .height(50.dp)
                            /* .padding(start = 33.dp)*/,
                            value = searchText,
                            onValueChange = { inputText -> searchText = inputText },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = colorResource(id = if (!searchClicked) R.color.white else R.color.white_smoke)
                            ),
                            textStyle = MaterialTheme.typography.overline.copy(
                                fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                                color = colorResource(id = R.color.deep_sky_blue),
                            )
                        )
                        androidx.compose.material.Text(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 284.dp),
                            text = stringResource(id = R.string.brand_search_cancel),
                            style = MaterialTheme.typography.overline.copy(
                                fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                                color = colorResource(id = R.color.deep_sky_blue),
                            ),
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun ProductHeader() {
    Row(
        Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = { /* TODO */ }, modifier = Modifier.padding(start = 16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_back), contentDescription = "back"
            )
        }
        IconButton(
            onClick = { /* TODO */ }, /*modifier = Modifier.padding(start = 16.dp)*/
        ) {
            Image(
                painter = painterResource(R.drawable.product_icon),
                contentDescription = "Share",

                )
        }

        Box(
            Modifier.weight(1f)
//                .padding(horizontal = 16.dp)
        ) {
            androidx.compose.material.Text(
                text = stringResource(
                    id = R.string.product_brands_title
                ), style = MaterialTheme.typography.subtitle1.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    color = colorResource(id = R.color.prussian_blue),
                ), modifier = Modifier.align(Alignment.Center)
            )
        }
        IconButton(
            onClick = { /* TODO */ }, /*modifier = Modifier.padding(start = 16.dp)*/
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_heart_blue),
                contentDescription = "Share",
                tint = colorResource(id = R.color.prussian_blue)
            )
        }
        IconButton(
            onClick = { /* TODO */ }, modifier = Modifier.padding(start = 16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_share),
                contentDescription = "Share",
                tint = colorResource(id = R.color.prussian_blue)
            )
        }
    }

}

@Composable
fun BazarCategoryList() {
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
        )
    )
    Box(Modifier.padding(top = 4.dp)) {
        LazyRow(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentWidth()
                .height(43.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(productsList.size) { index ->
                val product = productsList[index]
                var isCategoryClicked by remember { mutableStateOf(false) }
                IconButton(
                    onClick = {isCategoryClicked=!isCategoryClicked},
                    Modifier
                        .width(110.dp)
                        .height(31.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .border(
                            1.dp,
                            colorResource(id = if (isCategoryClicked) R.color.prussian_blue else R.color.Gray55),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .background(colorResource( id = if (isCategoryClicked) R.color.prussian_blue else R.color.white)),
                ) {
                    Text(
                        text = stringResource(id = R.string.product_bazar_category),
                        style = MaterialTheme.typography.overline.copy(
                            fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                            color = if(isCategoryClicked)colorResource(id = R.color.white) else colorResource(id = R.color.Gray55)

                        )
                    )
                }
            }
        }
    }
}



