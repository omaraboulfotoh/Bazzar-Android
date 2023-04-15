package com.bazzar.android.presentation.home_screen.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.model.home.Brand
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.HeaderTextWithViewAll
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FeaturedBrands(brandsList: List<Brand>?) {
    HeaderTextWithViewAll(text = stringResource(id = R.string.home_screen_featured_brands))
    LazyRow(
        modifier = Modifier
            .padding(top = 16.dp)
            .wrapContentWidth()
            .height(120.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        brandsList?.let {
            items(it) { product ->
                GlideImage(
                    model = product.imagePath,
                    contentDescription = "Product image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(104.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    )
            }
        }
    }
}
