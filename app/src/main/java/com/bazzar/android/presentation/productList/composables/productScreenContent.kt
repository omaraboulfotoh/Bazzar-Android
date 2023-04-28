package com.bazzar.android.presentation.productList.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.ProductItem
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.productList.ProductContract
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.theme.Shapes

@Composable
fun ProductScreenContent(
    state: ProductContract.State, onSendEvent: (ProductContract.Event) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor)
            .padding(bottom = BottomNavigationHeight),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
    ) {
        BazzarAppBar(onNavigationClick = {

        }, actions = {
            Icon(
                modifier = Modifier
                    .padding(BazzarTheme.spacing.xs)
                    .clickable {
                        onSendEvent(ProductContract.Event.OnSearchClicked)
                    },
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = null,
                tint = colorResource(id = R.color.prussian_blue)
            )
        })
        // show subCategories
        if (state.subCategoryList.isNullOrEmpty().not()) {
            SubCategorySlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(94.dp),
                subCategoryList = state.subCategoryList.orEmpty(),
                onClickSubCategory = { categoryIndex ->
                    onSendEvent(
                        ProductContract.Event.OnSubCategoryClicked(categoryIndex)
                    )
                }
            )
        }
        // show brands
        if (state.brand != null) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(horizontal = BazzarTheme.spacing.m)
            ) {
                BrandImage(
                    state.brand,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(Shapes.large)
                )
            }
        }

        // list items
        LazyVerticalGrid(
            modifier = Modifier.weight(1f),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(BazzarTheme.spacing.m),
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs),
            horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs),
        ) {
            itemsIndexed(state.productList.orEmpty()) { index, item ->
                ProductItem(item) {
                    onSendEvent(ProductContract.Event.OnProductClicked(index))
                }
            }
        }
    }
}









