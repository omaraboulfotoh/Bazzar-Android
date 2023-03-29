package com.bazzar.android.presentation.home_screen.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.model.movie.ProductModel
import com.bazzar.android.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState


@Preview
@Composable
fun HomeScreenContent(/*state: HomeContract.State*/) {
    LazyColumn(
        modifier = Modifier
            .background(colorResource(id = R.color.white_smoke))
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Header()
            BazzarHorizontalList()
            FeaturedBazzar()
            FeaturedBrands()
            ProductsGroup()
        }
    }
}

@Composable
fun Header() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(
                top = 32.dp, start = 127.dp
            ),
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(R.drawable.bazzars_home_title),
            contentDescription = "HomeScreenTitle",
        )
        Spacer(modifier = Modifier.width(85.dp))
        Image(
            painter = painterResource(R.drawable.search_icon),
            contentDescription = "searchIcon",
        )

    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BazzarHorizontalList() {

    val imageList = listOf(
        R.drawable.first_bazzar,
        R.drawable.second_bazzar,
        R.drawable.third_bazzar,
    )
    val pagerState = rememberPagerState(
        initialPage = 0,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(171.dp)
            .padding(top = 28.dp)
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
                    .fillMaxWidth()
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
fun FeaturedBazzar() {

    HeaderTextWithViewAll(text = stringResource(id = R.string.home_screen_featured_bzarz))
    val imageList = listOf(
        R.drawable.first_bazzar,
        R.drawable.second_bazzar,
        R.drawable.third_bazzar,
        R.drawable.third_bazzar,
        R.drawable.third_bazzar,
    )
    val textList = listOf(
        "Bzar Name",
        "Bzar Name",
        "Bzar Name"
    )
    CustomLazyRow(
        imageList = imageList,
        textList = textList,
        customIV = { image, text ->
            Column {
                SemiCircleImageView(
                    image = image,
                    text = text
                )
            }
        },
        topPadding = 16.dp,
        spaceBetweenItems = 16.dp
    )
}

@Composable
fun FeaturedBrands(/*brandsList: List<BrandModel>*/) {
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
    HeaderTextWithViewAll(text = stringResource(id = R.string.home_screen_featured_brands))
    LazyRow(
        modifier = Modifier
            .padding(top = 16.dp)
            .wrapContentWidth()
            .height(120.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(productsList.size) { index ->
            val product = productsList[index]
            Image(
                painter = painterResource(id = product.localPoster ?: -1),
                contentDescription = "Brand image",
                modifier = Modifier
                    .size(104.dp)
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Composable
fun ProductsGroup(/*productsList: List<ProductModel>*/) {
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
    HeaderTextWithViewAll(text = stringResource(id = R.string.home_screen_products_group))
    LazyRow(
        modifier = Modifier
            .padding(top = 16.dp)
            .wrapContentWidth()
            .height(322.dp),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        contentPadding = PaddingValues(horizontal = 7.dp),
    ) {
        items(productsList.size) { index ->
            val product = productsList[index]
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
                    Image(
                        painter = painterResource(id = product.localPoster ?: -1),
                        contentDescription = "Product image",
                        modifier = Modifier
                            .size(152.dp)
//                            .clip(RoundedCornerShape(8.dp))
                            .padding(top = 40.dp)
                        /*,contentScale = ContentScale.FillBounds*//*, alignment = Alignment.Center*/

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
                            .align(Alignment.Start),
                        text =
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontFamily =
                                    FontFamily(Font(R.font.montserrat_bold))
                                )
                            ) {
                                append(product.priceBeforeSale.toString() ?: "")
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
/*
                    Text(
                        text = stringResource(
                            id = R.string.home_screen_product_price,
                            product.priceBeforeSale.toString() ?: ""
                        ),
                        modifier = Modifier
                            .paddingFromBaseline(top = 24.dp)
                            .padding(start = 8.dp)
                            .padding(bottom = 32.dp)
                            .align(Alignment.Start),
                        style = MaterialTheme.typography.subtitle2.copy(
                            fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                            color = colorResource(id = R.color.black)
                        )
                    )
*/
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