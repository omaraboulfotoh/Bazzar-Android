package com.bazzar.android.presentation.wishlist.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.bazzar.android.presentation.theme.BazzarTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WishListPagerTabs(
    tabTitles: List<String>,
    pagerState: PagerState,
    currentPage: Int = 0,
    onTabClicked: ((Int) -> Unit)? = null,
    modifier: Modifier = Modifier
) {

    // Main objects

    // Prepare required width & height calculations
    var layoutSize by remember { mutableStateOf(IntSize.Zero) }
    val layoutHeight = 40.dp
    val indicatorWidth = with(LocalDensity.current) {
        layoutSize.width.divOrZero(pagerState.pageCount).toDp()
    }

    // Render tabs container
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(layoutHeight)
            .onSizeChanged {
                layoutSize = it
            },
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(1.dp), color = BazzarTheme.colors.stroke
        )
        // Moving black indicator
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.fillMaxSize(),
            activeColor = BazzarTheme.colors.black,
            inactiveColor = BazzarTheme.colors.transparentColor,
            indicatorHeight = layoutHeight,
            indicatorWidth = indicatorWidth,
            spacing = 0.dp,
            indicatorShape = RoundedCornerShape(24.dp)
        )

        // Tab titles
        Row(modifier = Modifier.fillMaxSize()) {
            tabTitles.forEachIndexed { index, title ->
                PagerTabItem(
                    title = title,
                    isSelected = index == currentPage,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        // Notify the listener
                        onTabClicked?.invoke(index)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun PagerTabsPreview() {
    WishListPagerTabs(
        tabTitles = listOf("hello", "world"),
        pagerState = PagerState(1),
        onTabClicked = {}
    )
}

fun Int.divOrZero(number: Int): Int {
    return if (number == 0) 0
    else this / number
}

