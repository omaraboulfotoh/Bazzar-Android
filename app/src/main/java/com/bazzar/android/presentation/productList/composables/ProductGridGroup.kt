package com.bazzar.android.presentation.productList.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.model.home.Product
import com.bazzar.android.presentation.composables.ProductItem

@Composable
fun ProductGridGroup(
    productsList: List<Product>?,
    modifier: Modifier,
    onItemClicked: (Int) -> Unit
) {
    Box(
        modifier
            .height(800.dp)
    ) {

    }
}
