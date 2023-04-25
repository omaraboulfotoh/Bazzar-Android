package com.bazzar.android.presentation.home_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.model.home.Brand
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.HeaderTextWithViewAll
import com.bazzar.android.presentation.composables.RemoteImage
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.theme.Shapes
import com.bazzar.android.presentation.theme.Shapes_MediumX
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

fun LazyListScope.FeaturedBrands(brandsList: List<Brand>) {
    item {
        HeaderTextWithViewAll(text = stringResource(id = R.string.home_screen_featured_brands))
    }
    item {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = BazzarTheme.spacing.m),
            horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs),
            contentPadding = PaddingValues(end = BazzarTheme.spacing.xs)
        ) {
            items(brandsList) { product ->
                Card(
                    modifier = Modifier
                        .size(110.dp)
                        .padding(BazzarTheme.spacing.xxs),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    content = {
                        RemoteImage(
                            imageUrl = product.imagePath,
                            contentScale = ContentScale.Inside,
                            background = BazzarTheme.colors.white,
                            withShimmer = false,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                )
            }
        }
    }
}
