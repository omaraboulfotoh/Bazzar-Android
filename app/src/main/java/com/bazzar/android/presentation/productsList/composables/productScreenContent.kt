package com.bazzar.android.presentation.productsList.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.bazarDetail.BazarDetailContract
import com.bazzar.android.presentation.common.gridItems
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.ProductItem
import com.bazzar.android.presentation.composables.SuccessAddedToCart
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.productDetail.ProductDetailContract
import com.bazzar.android.presentation.productsList.ProductContract
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.theme.Shapes

@Composable
fun ProductScreenContent(
    state: ProductContract.State, onSendEvent: (ProductContract.Event) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor)
            .padding(bottom = BottomNavigationHeight)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
        ) {
            BazzarAppBar(title = state.productScreenTitle,
                onNavigationClick = {
                    onSendEvent(ProductContract.Event.OnBackIconClicked)
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .defaultMinSize(minWidth = 50.dp, minHeight = 50.dp)
                            .clickable {
                                onSendEvent(ProductContract.Event.OnSearchClicked)
                            }
                    ) {
                        Icon(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            painter = painterResource(id = R.drawable.search_icon),
                            contentDescription = null,
                            tint = colorResource(id = R.color.prussian_blue)
                        )
                    }
                })

            LazyColumn(
                modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.s)
            ) {
                // show subCategories
                if (state.subCategoryList.isNullOrEmpty().not()) {
                    item {
                        SubCategorySlider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(90.dp),
                            subCategoryList = state.subCategoryList.orEmpty(),
                            onClickSubCategory = { categoryIndex ->
                                onSendEvent(
                                    ProductContract.Event.OnSubCategoryClicked(categoryIndex)
                                )
                            }
                        )
                    }
                }
                // show brands
                if (state.brand != null) {
                    item {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .padding(horizontal = BazzarTheme.spacing.m)
                        ) {
                            BrandImage(
                                state.brand.sliderImagePath,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(Shapes.large)
                            )
                        }
                    }
                }
                item {
                    SortFilterBar(
                        numOfProducts = state.productList?.size,
                        numOfSelectedFilters = state.numOfSelectedFilter,
                        onFilterClicked = { onSendEvent(ProductContract.Event.OnFilterClicked) },
                        onSortClicked = { onSendEvent(ProductContract.Event.OnSortClicked) },
                    )
                }
                // list items
                if (state.showEmptyView) {
                    item {
                        Box(modifier = Modifier.weight(1f)) {
                            Image(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .align(Alignment.Center),
                                imageVector = ImageVector.vectorResource(R.drawable.ic_empty_view),
                                contentDescription = "ic_empty_view"
                            )
                        }
                    }
                } else {
                    gridItems(count = state.productList.orEmpty().size,
                        nColumns = 2,
                        horizontalArrangement = Arrangement.Start,
                        itemContent = { index ->
                            val item = state.productList.orEmpty()[index]
                            ProductItem(
                                item,
                                modifier = Modifier.padding(BazzarTheme.spacing.xs),
                                onItemClicked = {
                                    onSendEvent(ProductContract.Event.OnProductClicked(index))
                                }, onFavClicked = {
                                    onSendEvent(ProductContract.Event.OnProductFavClicked(index))
                                }, onAddToCartClicked = {
                                    onSendEvent(
                                        ProductContract.Event.OnProductAddToCartClicked(
                                            index
                                        )
                                    )
                                })
                            val isLastItem = index == state.productList.orEmpty().lastIndex
                            if (isLastItem && state.isLoadingMore.not() && state.hasMore) {
                                onSendEvent(ProductContract.Event.ReachedListEnd)
                            }

                        })
                }
            }
        }

        SortDialog(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = BazzarTheme.spacing.m)
                .padding(bottom = BazzarTheme.spacing.m)
                .align(Alignment.BottomCenter),
            show = state.showSortDialog,
            sortingList = state.sortFilter?.sortingList ?: listOf(),
            selectedSort = state.selectedSort,
            onDismiss = { onSendEvent(ProductContract.Event.OnDismissSortDialogClicked) },
            onSelectSortItem = { onSendEvent(ProductContract.Event.OnSortItemSelected(it)) },
            onApply = { onSendEvent(ProductContract.Event.OnApplySortClicked) }
        )
        SuccessAddedToCart(
            modifier = Modifier.align(Alignment.BottomCenter),
            show = state.showSuccessAddedToCart,
            onContinueShoppingClick = { onSendEvent(ProductContract.Event.OnContinueShoppingClicked) },
            onVisitCardClick = { onSendEvent(ProductContract.Event.OnVisitYourCartClicked) },
        )

        FilterDialog(
            modifier = Modifier
                .fillMaxSize()
                .padding(BazzarTheme.spacing.m),
            show = state.showFilterDialog,
            selectedFilterType = state.selectedFilterType,
            selectedFiltersToShow = state.filterListToShow,
            numOfSelectedCategoryFilters = state.numOfSelectedCategoryFilters,
            numOfSelectedBrandFilters = state.numOfSelectedBrandFilters,
            numOfSelectedColorFilters = state.numOfSelectedColorFilters,
            numOfSelectedSizeFilters = state.numOfSelectedSizeFilters,
            minPrice = state.selectedFilterMinPrice ?: 0,
            maxPrice = state.selectedFilterMaxPrice ?: 1000,
            onFilterTypeClick = { onSendEvent(ProductContract.Event.OnFilterTypeClicked(it)) },
            onSelectUnselectFilter = { filter, isSelect ->
                onSendEvent(ProductContract.Event.OnSelectUnselectFilter(filter, isSelect))
            },
            onApply = { onSendEvent(ProductContract.Event.OnApplyFiltersClicked) },
            onRest = { onSendEvent(ProductContract.Event.OnResetFiltersClicked) },
            onDismiss = { onSendEvent(ProductContract.Event.OnDismissFilterDialogClicked) },
            onMinPriceChanged = { onSendEvent(ProductContract.Event.OnMinPriceChanged(it)) },
            onMaxPriceChanged = { onSendEvent(ProductContract.Event.OnMaxPriceChanged(it)) },
        )
    }
}