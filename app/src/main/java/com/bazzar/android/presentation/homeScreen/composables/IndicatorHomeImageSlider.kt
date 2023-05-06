package com.bazzar.android.presentation.homeScreen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.RemoteImageCard
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.theme.Shapes
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun IndicatorHomeImageSlider(
    imagePathList: List<String>,
    modifier: Modifier = Modifier,
    onSliderClicked: (Int) -> Unit
) {

    val pagerState = rememberPagerState()

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.s)

    ) {
        HorizontalPager(
            state = pagerState,
            count = imagePathList.size,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            contentPadding = PaddingValues(4.dp)

        ) { page ->
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(BazzarTheme.spacing.xs)
                    .clickable { onSliderClicked(page) },
                shape = Shapes.large,
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
                elevation = CardDefaults.cardElevation(defaultElevation = BazzarTheme.spacing.xs),
                content = {
                    RemoteImageCard(
                        imageUrl = (imagePathList[page]),
                        contentScale = ContentScale.FillWidth,
                        withShimmer = true,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                            .padding(BazzarTheme.spacing.xxs)
                    )
                }
            )
        }
        if (imagePathList.size > 1)
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .wrapContentSize(),
                activeColor = BazzarTheme.colors.indicatorActiveColor,
                inactiveColor = BazzarTheme.colors.indicatorInActiveColor,
                indicatorWidth = BazzarTheme.spacing.xs,
                indicatorHeight = BazzarTheme.spacing.xs,
            )
    }
}