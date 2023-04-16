package com.bazzar.android.presentation.product_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.product_screen.ProductContract

@Composable
fun ProductScreenContent(
    state: ProductContract.State, onSendEvent: (ProductContract.Event) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white_smoke)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ProductHeader(state.ProductScreenTitle, Modifier.padding(top = 32.dp))
        }
        item {
            SearchProduct(
                searchClicked = state.isSearchClicked,
                Modifier.height(41.dp),
                onSearchClicked = {
                    onSendEvent(
                        ProductContract.Event.OnSearchClicked
                    )
                }
            )
        }

        item {
            BrandImage(state.brand, Modifier.padding(top = 26.dp))
        }
        item {
            SubCategorySlider(
                state.subCategoryList,
                state.isSubCategoryClicked,
                onClickSubCategory = { categoryIndex ->
                    onSendEvent(
                        ProductContract.Event.OnSubCategoryClicked(categoryIndex)
                    )
                }, modifier = Modifier.padding(top = 29.dp)
            )
        }
        item {
            SortFilterBar(
                numberFilteredProducts = state.filteredProductList?.size,
                numberSelectedCategory = state.selectedSubCategoryList?.size ?: 0,
                Modifier.height(10.dp),
                onFilterClicked = {
                    onSendEvent(
                        ProductContract.Event.OnFilterClicked
                    )
                }, onSortClicked = {
                    onSendEvent(
                        ProductContract.Event.OnSortClicked
                    )
                }
            )
        }
        item {
            ProductGridGroup(state.filteredProductList, modifier = Modifier.padding(top = 16.dp))
        }
    }

}









