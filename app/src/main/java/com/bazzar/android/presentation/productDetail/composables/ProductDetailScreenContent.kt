package com.bazzar.android.presentation.productDetail.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.common.orFalse
import com.bazzar.android.common.orZero
import com.bazzar.android.common.toPriceFormat
import com.bazzar.android.presentation.composables.IndicatorImageSlider
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.homeScreen.composables.ProductsGroup
import com.bazzar.android.presentation.productDetail.ProductDetailContract.*
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun ProductDetailScreenContent(
    state: State,
    onSendEvent: (Event) -> Unit,
) {
    Column {
        Box {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = BottomNavigationHeight)
                    .background(BazzarTheme.colors.backgroundColor),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs)
            ) {
                item {
                    IndicatorImageSlider(imagePathList = state.selectedColoredImagesList,
                        showNewBadge = state.productDetail?.isNew.orFalse(),
                        showExclusiveBadge = state.productDetail?.isExclusive.orFalse(),
                        showDiscountBadge = state.productDetail?.discountPercentage != null,
                        discount = state.productDetail?.discountPercentage,
                        onBackClicked = {
                            onSendEvent(Event.OnBackIconClicked)
                        },
                        onShareClicked = {
                            onSendEvent(Event.OnShareClicked)
                        })
                }
                item {
                    BrandSection(productTitle = state.productDetail?.title.orEmpty(),
                        brandName = state.productDetail?.brandTitle.orEmpty(),
                        brandImagePath = state.productDetail?.brandImagePath.orEmpty(),
                        newPrice = state.productDetail?.price?.toString(),
                        oldPrice = state.productDetail?.oldPrice.orZero().toPriceFormat(),
                        onBrandClicked = {
                            onSendEvent(Event.OnSeeMoreBrandClicked)
                        })
                }
                item {
                    AvailableColorSizeProduct(isColorItemClicked = false,
                        isSizeClicked = false,
                        itemImages = state.productDetail?.itemImages?.distinctBy { it.colorId }
                            ?.map { it.imagePath ?: "" } ?: listOf(),
                        sizeList = state.selectedSizeTitleList,
                        onColorSelected = { index ->
                            Event.OnColorItemSelected(
                                index
                            )
                        },
                        onSizeSelected = { index ->
                            Event.OnSizeItemSelected(index)
                        })
                }
                item {
                    ProductDescription(
                        modifier = Modifier.padding(horizontal = BazzarTheme.spacing.l),
                        isTextExpanded = state.isTextExpanded,
                        text = state.productDetail?.description.orEmpty(),
                        sku = state.selectedItemDetail?.sku.orEmpty(), onReadMoreLessClicked = {
                            onSendEvent(Event.OnSeeMoreClicked)
                        }
                    )
                }
                item {
                    // top space
                    Spacer(modifier = Modifier.height(BazzarTheme.spacing.xs))

                    // products list
                    ProductsGroup(productsList = state.productDetail?.relatedItems.orEmpty(),
                        headerTitle = stringResource(id = R.string.related_product),
                        onProductClicked = { itemIndex ->
                            Event.OnRelatedItemClicked(itemIndex)
                        })
                }
                item {
                    Spacer(modifier = Modifier.height(BazzarTheme.spacing.m))
                }
            }
            BuyItem(modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp),
                onBuyNowClicked = { onSendEvent(Event.OnBuyNowClicked) })
        }
    }
}









