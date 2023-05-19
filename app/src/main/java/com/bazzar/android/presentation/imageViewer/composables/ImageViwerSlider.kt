package com.bazzar.android.presentation.imageViewer.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bazzar.android.presentation.composables.RemoteImage
import com.bazzar.android.presentation.theme.BazzarTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageViewerSlider(
    modifier: Modifier = Modifier,
    imagePathList: List<String>,
    currentIndex: Int,
) {

    val pagerState = rememberPagerState()

    LaunchedEffect(currentIndex) {
        pagerState.animateScrollToPage(currentIndex)
    }

    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        count = imagePathList.size,
    ) { page ->
        RemoteImage(
            modifier = Modifier.fillMaxSize(),
            imageUrl = imagePathList[page],
            contentScale = ContentScale.Fit,
            background = BazzarTheme.colors.white,
            withShimmer = true,
        )
    }
}