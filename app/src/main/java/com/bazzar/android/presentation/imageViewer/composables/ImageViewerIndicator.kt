package com.bazzar.android.presentation.imageViewer.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bazzar.android.presentation.composables.RemoteImage
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun ImageViewerIndicator(
    modifier: Modifier = Modifier,
    imagePathList: List<String>,
    currentIndex: Int,
    onImageClick: (index: Int) -> Unit,
) {

    val lazyRowState = rememberLazyListState()

    LaunchedEffect(currentIndex) {
        lazyRowState.animateScrollToItem(currentIndex)
    }

    LazyRow(
        modifier = modifier,
        state = lazyRowState
    ) {
        itemsIndexed(imagePathList) { index, item ->
            RemoteImage(
                modifier = Modifier
                    .padding(horizontal = BazzarTheme.spacing.xxs)
                    .border(
                        width = 1.dp,
                        color =
                        if (currentIndex == index) BazzarTheme.colors.primaryButtonColor
                        else BazzarTheme.colors.stroke,
                        shape = RoundedCornerShape(22.dp)
                    )
                    .size(168.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .clickable { onImageClick(index) },
                imageUrl = item,
                contentScale = ContentScale.Fit,
                background = BazzarTheme.colors.white,
                withShimmer = true,
            )
        }
    }
}