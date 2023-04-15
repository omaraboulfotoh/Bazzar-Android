package com.bazzar.android.presentation.home_screen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.android.model.home.HomeSlider
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.Indicator
import com.bazzar.android.presentation.composables.RemoteImage
import com.bazzar.android.presentation.home_screen.HomeContract
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun BazzarSlider(homeSliderList: List<HomeSlider>?, onSliderClicked: (Int) -> Unit) {

    val pagerState = rememberPagerState()

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
            Card(
                modifier = Modifier
                    .width(343.dp)
                    .height(147.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
                elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
                content = {
                    GlideImage(
                        model = (homeSliderList?.get(page)?.imagePath),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(147.dp)
//                            .height(147.dp)
                            .padding(horizontal = 4.dp)
                            .clickable { onSliderClicked(page) },
                        contentDescription = null
                    )
                }
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
