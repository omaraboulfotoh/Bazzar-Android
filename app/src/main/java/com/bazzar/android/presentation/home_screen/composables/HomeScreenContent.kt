package com.bazzar.android.presentation.home_screen.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
            ), horizontalArrangement = Arrangement.Start
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
            contentPadding = PaddingValues(horizontal = 4.dp)

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

