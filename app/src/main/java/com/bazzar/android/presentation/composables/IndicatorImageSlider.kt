package com.bazzar.android.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.productsList.composables.DiscountView
import com.bazzar.android.presentation.productsList.composables.ExclusiveView
import com.bazzar.android.presentation.productsList.composables.NewBadgeView
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.theme.Shapes
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun IndicatorImageSlider(
    modifier: Modifier = Modifier,
    imagePathList: List<String>,
    showNewBadge: Boolean,
    showExclusiveBadge: Boolean,
    showDiscountBadge: Boolean = false,
    discount: Double? = 0.0,
    onBackClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onImageClicked: (index: Int) -> Unit,
) {
    val pagerState = rememberPagerState()
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(375.dp),
        shape = Shapes.large,
        backgroundColor = BazzarTheme.colors.white,

        ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.s)

        ) {
            BazzarAppBar(onNavigationClick = { onBackClicked() },
                actions = {
                    Box(modifier = Modifier
                        .defaultMinSize(minWidth = 50.dp, minHeight = 50.dp)
                        .clickable { onShareClicked() }) {
                        Icon(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = null,
                            tint = colorResource(id = R.color.prussian_blue)
                        )
                    }
                })
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                HorizontalPager(
                    state = pagerState,
                    count = imagePathList.size,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = BazzarTheme.spacing.m)

                ) { page ->
                    RemoteImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { onImageClicked(page) },
                        imageUrl = (imagePathList[page]),
                        contentScale = ContentScale.Crop,
                        background = BazzarTheme.colors.white,
                        withShimmer = true,
                    )
                }


                // badges
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterEnd),
                    verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m),
                    horizontalAlignment = Alignment.End
                ) {
                    if (showDiscountBadge) {
                        DiscountView(
                            modifier = Modifier.size(width = 70.dp, height = 24.dp),
                            discount.orZero()
                        )
                    }
                    if (showExclusiveBadge) {
                        ExclusiveView(modifier = Modifier.size(width = 70.dp, height = 24.dp))
                    }
                    if (showNewBadge) {
                        NewBadgeView(
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

            }
            if (imagePathList.size > 1) {
                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier.wrapContentSize(),
                    activeColor = BazzarTheme.colors.indicatorActiveColor,
                    inactiveColor = BazzarTheme.colors.indicatorInActiveColor,
                    indicatorWidth = BazzarTheme.spacing.xs,
                    indicatorHeight = BazzarTheme.spacing.xs,
                )
                Spacer(modifier = Modifier.height(BazzarTheme.spacing.m))
            }
        }
    }
}
