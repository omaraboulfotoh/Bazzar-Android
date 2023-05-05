package com.bazzar.android.presentation.productsList.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.android.model.home.Brand
import com.bazzar.android.presentation.composables.RemoteImageCard
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun BrandImage(brand: Brand?, modifier: Modifier) {
    RemoteImageCard(
        imageUrl = brand?.imagePath,
        contentScale = ContentScale.FillWidth,
        modifier = modifier,
        background = BazzarTheme.colors.white
    )
}
