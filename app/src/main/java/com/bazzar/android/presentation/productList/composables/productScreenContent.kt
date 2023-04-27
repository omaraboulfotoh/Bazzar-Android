package com.bazzar.android.presentation.productList.composables

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
import com.bazzar.android.presentation.productList.ProductContract

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
            SearchProduct(searchClicked = state.isSearchClicked,
                Modifier.height(41.dp),
                onSearchClicked = {
                    onSendEvent(
                        ProductContract.Event.OnSearchClicked
                    )
                })
        }

        item {
            BrandImage(state.brand, Modifier.padding(top = 26.dp))
        }
        item {
            SubCategorySlider(
                state.subCategoryList, onClickSubCategory = { categoryIndex ->
                    onSendEvent(
                        ProductContract.Event.OnSubCategoryClicked(categoryIndex)
                    )
                }, modifier = Modifier.padding(top = 29.dp)
            )
        }
        item {
            ProductGridGroup(
                state.productList,
                modifier = Modifier.padding(top = 16.dp),
                onItemClicked = {
                    onSendEvent(ProductContract.Event.OnProductClicked(it))
                })
        }
    }

}









