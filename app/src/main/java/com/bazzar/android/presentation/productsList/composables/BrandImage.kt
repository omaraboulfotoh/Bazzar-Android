package com.bazzar.android.presentation.productsList.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.android.model.home.Brand
import com.bazzar.android.presentation.composables.RemoteImage
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.theme.Shapes

@Composable
fun BrandImage(brand: Brand?, modifier: Modifier) {
    RemoteImage(
        imageUrl = brand?.imagePath,
        contentScale = ContentScale.FillWidth,
        modifier = modifier,
        background = BazzarTheme.colors.white
    )
}
