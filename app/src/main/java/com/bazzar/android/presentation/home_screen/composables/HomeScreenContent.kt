package com.bazzar.android.presentation.home_screen.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun Indicator(selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(if (selected) colorResource(id = R.color.prussian_blue) else Color.White)
            .clickable(onClick = onClick)
    )
    Spacer(modifier = Modifier.width(2.dp))
}

@Composable
fun FeaturedBazzar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = stringResource(id = R.string.home_screen_featured_bzarz),
            style = MaterialTheme.typography.subtitle1.copy(
                fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                color = colorResource(id = R.color.prussian_blue),
            )
        )
        Text(
            text = stringResource(id = R.string.home_screen_view_all),
            style = MaterialTheme.typography.caption.copy(
                fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                color = colorResource(id = R.color.deep_sky_blue)
            )
        )
    }
    LazyRow(
        modifier = Modifier
            .padding(top = 16.dp)
            .wrapContentWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
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
        val imageTextList = imageList.zip(textList)
        items(imageTextList) {
            CustomImageView(it)
        }
    }
}

@Composable
fun CustomImageView(imageTextPair: Pair<Int, String>) {
    Box(
        modifier = Modifier
            .width(136.dp)
            .height(215.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 68.dp,
                    topEnd = 68.dp,
                    bottomStart = 22.dp,
                    bottomEnd = 22.dp
                )
            )
            .background(colorResource(id = R.color.prussian_blue))
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Unspecified, CircleShape)
                .padding(all = 5.dp)
                .size(126.dp)
                .align(Alignment.TopCenter),
        ) {
            Image(
                painter = painterResource(id = imageTextPair.first),
                contentDescription = null,
                contentScale = ContentScale.Crop, modifier = Modifier
                    .width(126.dp)
                    .height(126.dp)
                    .clip(CircleShape)
            )

        }
        Text(
            text = imageTextPair.second,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 55.dp)
                .align(Alignment.BottomEnd),
            style = MaterialTheme.typography.subtitle2.copy(
                fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                color = colorResource(id = R.color.white)
            )
        )
    }
}

/*
@Composable
fun AspectFillImage(
    image: Painter,
    modifier: Modifier = Modifier,
) {
    val imageWidth = image.intrinsicSize.width
    val imageHeight = image.intrinsicSize.height
    val ratio = imageWidth / imageHeight

    Box(
        modifier = modifier
            .aspectRatio(ratio)
    ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier
                .scale(
                    max(imageWidth / size.width, imageHeight / size.height)
                )
                .clip(RectangleShape)
        )
    }
}*/
