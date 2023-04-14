package com.bazzar.android.presentation.home_screen.composables

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.model.home.HomeSlider
import com.bazzar.android.presentation.composables.Indicator
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun BazzarSlider(homeSliderList: List<HomeSlider>?) {

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
            count = homeSliderList?.size ?: 0,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(4.dp)

        ) { page ->
            GlideImage(
                model = (homeSliderList?.get(page)?.imagePath),
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
            homeSliderList?.let {
                for (i in it.indices) {
                    Indicator(selected = i == pagerState.currentPage) {}
                }
            }
        }
    }
}
