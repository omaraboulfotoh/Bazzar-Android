package com.bazzar.android.presentation.homeScreen.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.model.home.Product
import com.bazzar.android.presentation.composables.HeaderTextWithViewAll
import com.bazzar.android.presentation.composables.ProductItem
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun ProductsGroup(
    headerTitle: String,
    productsList: List<Product>,
    onProductClicked: (Int) -> Unit
) {

    // title
    HeaderTextWithViewAll(text = headerTitle)
    // space
    Spacer(modifier = Modifier.height(BazzarTheme.spacing.m))
    // products list
    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .height(322.dp),
        horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs),
        contentPadding = PaddingValues(horizontal = BazzarTheme.spacing.xs),
    ) {

        itemsIndexed(productsList) { index, product ->
            ProductItem(product,
                onItemClicked = { onProductClicked(index) })
        }
    }
}

