package com.bazzar.android.presentation.product_detail_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.home_screen.composables.IndicatorImageSlider
import com.bazzar.android.presentation.home_screen.composables.ProductsGroup
import com.bazzar.android.presentation.product_detail_screen.composables.BrandSection
import com.bazzar.android.presentation.product_detail_screen.composables.ProductDescription
import com.bazzar.android.presentation.product_detail_screen.composables.RatingRow
import com.bazzar.android.presentation.product_screen.ProductDetailContract

@Composable
fun ProductDetailScreenContent(
    state: ProductDetailContract.State, onSendEvent: (ProductDetailContract.Event) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white_smoke)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            IndicatorImageSlider(
                imagePathList = state.product.imagePath as List<String>,
                columnModifier = Modifier
                    .width(375.dp)
                    .height(300.dp),
                imageCardModifier = Modifier.size(280.dp),
                onSliderClicked = {
                    onSendEvent(
                        ProductDetailContract.Event.OnSliderClicked(0, it)
                    )
                }
            )
        }
        item {
            BrandSection(
                productTitle = state.product.title ?: "",
                brandName = state.product.brandTitle ?: "",
                brandImagePath = state.brand.imagePath ?: "",
                newPrice = state.product.price.toString(),
                oldPrice = state.product.oldPrice.toString(),
                modifier = Modifier
                    .padding(start = 16.dp)
                    .padding(top = 8.dp)
            )
        }
        item {

        }
        item {
            RatingRow(
                rating = state.rating,
                updateRating = { index -> ProductDetailContract.Event.OnRatingClicked(index) })
        }
        item {
            ProductDescription()
        }
        item {
            ProductsGroup(
                productsList = state.relatedProductList,
                headerTitle = stringResource(id = R.string.related_product)
            )
        }
    }
}









