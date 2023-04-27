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
import com.android.model.home.toProduct
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.IndicatorImageSlider
import com.bazzar.android.presentation.homeScreen.composables.ProductsGroup
import com.bazzar.android.presentation.product_detail_screen.ProductDetailContract

@Composable
fun ProductDetailScreenContent(
    state: ProductDetailContract.State, onSendEvent: (ProductDetailContract.Event) -> Unit,
) {
    Column {
        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.white_smoke)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    IndicatorImageSlider(
                        imagePathList = state.selectedColoredImagesList,
                        modifier = Modifier.wrapContentHeight(),
                        onSliderClicked = {
                            onSendEvent(
                                ProductDetailContract.Event.OnSliderClicked(0, it)
                            )
                        }
                    )
                }
                item {
                    BrandSection(
                        productTitle = state.productDetail.title ?: "",
                        brandName = state.productDetail.brandTitle ?: "",
                        brandImagePath = state.productDetail.brandImagePath ?: "",
                        newPrice = state.productDetail.price.toString(),
                        oldPrice = state.productDetail.oldPrice.toString(),
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .padding(top = 8.dp)
                    )
                }
                item {
                    AvailableColorSizeProduct(
                        isColorItemClicked = state.isColorItemClicked,
                        isSizeClicked = state.isSizeClicked,
                        itemImages = state.selectedColoredImagesList,
                        sizeList = state.selectedSizeTitleList,
                        onColorSelected = { index ->
                            ProductDetailContract.Event.OnColorItemSelected(
                                index
                            )
                        },
                        onSizeSelected = { index ->
                            ProductDetailContract.Event.OnSizeItemSelected(index)
                        }
                    )
                }
                item {
                    RatingRow(
                        rating = state.rating,
                        updateRating = { index -> ProductDetailContract.Event.OnRatingClicked(index) })
                }
                item {
                    ProductDescription(text = state.productDetail.description ?: "")
                }
                item {
                    ProductsGroup(
                        productsList = state.productDetail.relatedItems.map { it.toProduct() },
                        headerTitle = stringResource(id = R.string.related_product),
                        onProductClicked = { itemId ->
                            ProductDetailContract.Event.OnRelatedItemClicked(
                                itemId
                            )
                        }
                    )
                }
            }
            BuyItem(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp),
                itemDetailId = state.selectedItemDetailId,
                onBuyNowClicked = { itemDetailId ->
                    onSendEvent(
                        ProductDetailContract.Event.OnBuyNowClicked(itemDetailId)
                    )
                })
        }
    }
}









