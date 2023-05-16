package com.bazzar.android.presentation.imageViewer.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bazzar.android.common.advancedShadow
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.BuyItem
import com.bazzar.android.presentation.composables.SuccessAddedToCart
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.imageViewer.ImageViewerContract
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun ImageViewerScreenContent(
    state: ImageViewerContract.State,
    onSendEvent: (ImageViewerContract.Event) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor)
            .padding(bottom = BottomNavigationHeight)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            BazzarAppBar(
                backgroundColor = BazzarTheme.colors.white,
                onNavigationClick = { onSendEvent(ImageViewerContract.Event.OnBackClicked) }
            )
            ImageViewerSlider(
                modifier = Modifier
                    .weight(1f)
                    .background(BazzarTheme.colors.white)
                    .padding(horizontal = BazzarTheme.spacing.m),
                imagePathList = state.imagePathsList,
                currentIndex = state.currentIndicatorIndex
            )
            ImageViewerIndicator(
                modifier = Modifier.padding(top = BazzarTheme.spacing.m),
                imagePathList = state.imagePathsList,
                currentIndex = state.currentIndicatorIndex,
                onImageClick = { onSendEvent(ImageViewerContract.Event.OnIndicatorClicked(it)) }
            )
        }
        BuyItem(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = BazzarTheme.spacing.m, bottom = BazzarTheme.spacing.m)
                .advancedShadow(
                    cornersRadius = 33.dp,
                    shadowBlurRadius = 15.dp,
                    alpha = 0.5f,
                    offsetX = 5.dp,
                    offsetY = 5.dp,
                ),
            onBuyNowClicked = { onSendEvent(ImageViewerContract.Event.OnBuyNowClicked) }
        )
        SuccessAddedToCart(
            modifier = Modifier.align(Alignment.BottomCenter),
            show = state.showSuccessAddedToCart,
            onContinueShoppingClick = { onSendEvent(ImageViewerContract.Event.OnContinueShoppingClicked) },
            onVisitCardClick = { onSendEvent(ImageViewerContract.Event.OnVisitYourCartClicked) },
        )
    }
}