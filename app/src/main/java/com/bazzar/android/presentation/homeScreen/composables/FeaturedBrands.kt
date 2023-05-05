package com.bazzar.android.presentation.homeScreen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.model.home.Brand
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.HeaderTextWithViewAll
import com.bazzar.android.presentation.composables.RemoteImageCard
import com.bazzar.android.presentation.theme.BazzarTheme

fun LazyListScope.FeaturedBrands(
    brandsList: List<Brand>,
    onBrandClicked: (Int) -> Unit
) {
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
            itemsIndexed(brandsList) { index, product ->
                Card(
                    modifier = Modifier
                        .size(110.dp)
                        .padding(BazzarTheme.spacing.xxs)
                        .clickable {
                            onBrandClicked(index)
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    content = {
                        RemoteImageCard(
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
