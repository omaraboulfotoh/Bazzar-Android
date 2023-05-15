package com.bazzar.android.presentation.categoryScreen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.android.model.home.Brand
import com.bazzar.android.R
import com.bazzar.android.presentation.common.gridItems
import com.bazzar.android.presentation.composables.RemoteImageCard
import com.bazzar.android.presentation.theme.BazzarTheme


fun LazyListScope.BrandGrid(
    brandList: List<Brand>,
    isSearchBrandOpen: Boolean = false,
    searchBrandTerm: String? = null,
    onSearchTermChanged: (String) -> Unit,
    onSearchClick: (Boolean) -> Unit,
    onCancelSearchClicked: () -> Unit,
    onBrandClicked: (Int) -> Unit,
) {
    item {
        Search(
            modifier = Modifier.padding(horizontal = BazzarTheme.spacing.m),
            isSearchOpen = isSearchBrandOpen,
            searchTerm = searchBrandTerm ?: "",
            onSearchTermChanged = onSearchTermChanged,
            onCancelSearchClicked = onCancelSearchClicked,
            onSearchClick = onSearchClick,
        )
    }
    gridItems(
        count = brandList.size,
        nColumns = 3,
        horizontalArrangement = Arrangement.Start,
        itemContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onBrandClicked(it) },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs)
            ) {
                val item = brandList[it]
                Card(
                    modifier = Modifier
                        .size(100.dp)
                        .clickable {
                            onBrandClicked(it)
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    content = {
                        RemoteImageCard(
                            imageUrl = item.imagePath,
                            contentScale = ContentScale.Fit,
                            background = BazzarTheme.colors.white,
                            withShimmer = false,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                )
                Text(
                    text = item.title ?: "",
                    style = MaterialTheme.typography.overline.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                        color = colorResource(id = R.color.black),
                    ),
                )
            }
        })
}

