package com.bazzar.android.presentation.home_screen.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.model.home.Product
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.HeaderTextWithViewAll
import com.bazzar.android.presentation.composables.ProductItem
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi

@Composable
fun ProductsGroup(productsList: List<Product>?) {
    HeaderTextWithViewAll(text = stringResource(id = R.string.home_screen_products_group))
    LazyRow(
        modifier = Modifier
            .padding(top = 16.dp)
            .wrapContentWidth()
            .height(322.dp),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        contentPadding = PaddingValues(horizontal = 7.dp),
    ) {
        productsList?.let {
            items(it) { product ->
                ProductItem(product)
            }
        }
    }
}

