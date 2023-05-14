package com.bazzar.android.presentation.productDetail.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.common.advancedShadow
import com.bazzar.android.common.orFalse
import com.bazzar.android.common.orZero
import com.bazzar.android.common.toPriceFormat
import com.bazzar.android.presentation.composables.IndicatorImageSlider
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.homeScreen.composables.ProductsGroup
import com.bazzar.android.presentation.productDetail.ProductDetailContract.Event
import com.bazzar.android.presentation.productDetail.ProductDetailContract.State
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun ProductDetailScreenContent(
    state: State,
    onSendEvent: (Event) -> Unit,
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BazzarTheme.colors.backgroundColor)
                .padding(bottom = BottomNavigationHeight)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs)
            ) {
                item {
                    IndicatorImageSlider(
                        imagePathList = state.selectedColoredImagesList,
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
                    AvailableColorSizeProduct(
                        itemColors = state.colorsList,
                        sizeList = state.sizeTitleList,
                        selectedDetail = state.selectedItemDetail,
                        onColorSelected = { index ->
                            onSendEvent(Event.OnColorItemSelected(index))
                        },
                        onSizeSelected = { index ->
                            onSendEvent(Event.OnSizeItemSelected(index))
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
                            onSendEvent(Event.OnRelatedItemClicked(itemIndex))

                        })
                }
                item {
                    Spacer(modifier = Modifier.height(BazzarTheme.spacing.m))
                }
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = BazzarTheme.spacing.m, bottom = BazzarTheme.spacing.m),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                TalkToUs(
                    modifier = Modifier.align(Alignment.End),
                    onClick = { onSendEvent(Event.OnTackToUsClicked) }
                )
                BuyItem(
                    modifier = Modifier
                        .align(Alignment.End)
                        .advancedShadow(
                            cornersRadius = 33.dp,
                            shadowBlurRadius = 15.dp,
                            alpha = 0.5f,
                            offsetX = 5.dp,
                            offsetY = 5.dp,
                        ),
                    onBuyNowClicked = { onSendEvent(Event.OnBuyNowClicked) }
                )
            }
            SuccessAddedToCart(
                modifier = Modifier.align(Alignment.BottomCenter),
                show = state.showSuccessAddedToCart,
                onContinueShoppingClick = { onSendEvent(Event.OnContinueShoppingClicked) },
                onVisitCardClick = { onSendEvent(Event.OnVisitYourCartClicked) },
            )
        }
    }
}