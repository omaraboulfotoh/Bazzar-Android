package com.bazzar.android.presentation.product_screen.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.model.home.Brand
import com.bazzar.android.presentation.composables.RemoteImage

@Composable
fun BrandImage(brand: Brand?, modifier: Modifier) {
    RemoteImage(
        imageUrl =brand?.imagePath,
        modifier = modifier
            .width(335.dp)
            .wrapContentHeight()
            .padding(horizontal = 4.dp),
    )
}
