package com.bazzar.android.presentation.bazarDetail.composables

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.bazarDetail.BazarDetailContract
import com.bazzar.android.presentation.common.gridItems
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.Caption
import com.bazzar.android.presentation.composables.ProductItem
import com.bazzar.android.presentation.composables.SearchTextInput
import com.bazzar.android.presentation.composables.SuccessAddedToCart
import com.bazzar.android.presentation.homeScreen.composables.IndicatorHomeImageSlider
import com.bazzar.android.presentation.productsList.composables.FilterDialog
import com.bazzar.android.presentation.productsList.composables.SortDialog
import com.bazzar.android.presentation.productsList.composables.SortFilterBar
import com.bazzar.android.presentation.productsList.composables.SubCategoryTextSlider
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun BazarDetailScreenContent(
    modifier: Modifier,
    state: BazarDetailContract.State,
    onSendEvent: (BazarDetailContract.Event) -> Unit,
) {

    val focusManager = LocalFocusManager.current

//    BackHandler {
//        onSendEvent(BazarDetailContract.Event.OnBackIconClicked)
//    }
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
        ) {
            BazzarAppBar(title = state.bazaar?.name.orEmpty(),
                onNavigationClick = { onSendEvent(BazarDetailContract.Event.OnBackIconClicked) },
                actions = {
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Box(modifier = Modifier
                            .defaultMinSize(minWidth = 40.dp, minHeight = 40.dp)
                            .clickable { onSendEvent(BazarDetailContract.Event.OnShareCLicked) }) {
                            Icon(
                                modifier = Modifier.align(Alignment.CenterEnd),
                                painter = painterResource(id = R.drawable.ic_share),
                                contentDescription = null,
                                tint = colorResource(id = R.color.prussian_blue)
                            )
                        }
                        Box(modifier = Modifier
                            .defaultMinSize(minWidth = 40.dp, minHeight = 40.dp)
                            .clickable { onSendEvent(BazarDetailContract.Event.OnFavouriteClicked) }) {
                            Image(
                                modifier = Modifier.align(Alignment.CenterEnd),
                                painter = painterResource(id = if (state.isFavourite) R.drawable.ic_fav_active else R.drawable.ic_fav),
                                contentDescription = null,
                            )
                        }
                    }

                })

            // search view
            Box(
                Modifier
                    .height(36.dp)
                    .padding(horizontal = BazzarTheme.spacing.m)
                    .fillMaxWidth()
            ) {
                SearchTextInput(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(24.dp))
                        .background(BazzarTheme.colors.white)
                        .border(
                            width = 0.5.dp,
                            color = BazzarTheme.colors.stroke,
                            shape = RoundedCornerShape(24.dp)
                        ),
                    hint = stringResource(id = R.string.product_search_title),
                    value = state.searchTerm ?: "",
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        focusManager.clearFocus()
                        onSendEvent(BazarDetailContract.Event.OnSearchClicked())
                    }),
                    childRowModifier = Modifier.padding(vertical = BazzarTheme.spacing.xs),
                    onValueChange = {
                        onSendEvent(BazarDetailContract.Event.OnSearchTermChanged(it))
                    },
                    textStyle = BazzarTheme.typography.captionBold.copy(color = BazzarTheme.colors.primaryButtonColor),
                    cursorColor = SolidColor(BazzarTheme.colors.primaryButtonColor),
                    leadingIcon = {
                        Icon(
                            modifier = Modifier
                                .padding(
                                    start = BazzarTheme.spacing.briefingIconsDimen,
                                    end = BazzarTheme.spacing.s
                                )
                                .clickable {
                                    focusManager.clearFocus()
                                    onSendEvent(BazarDetailContract.Event.OnSearchClicked())
                                },
                            painter = painterResource(id = R.drawable.search_icon),
                            tint = BazzarTheme.colors.primaryButtonColor,
                            contentDescription = ""
                        )
                    }
                )
                if (state.searchTerm.isNullOrEmpty().not())
                    Caption(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.CenterEnd)
                            .padding(horizontal = BazzarTheme.spacing.m)
                            .clickable {
                                focusManager.clearFocus()
                                onSendEvent(BazarDetailContract.Event.OnSearchClicked(""))
                            },
                        text = stringResource(id = R.string.cancel),
                        textAlign = TextAlign.Center,
                        isBold = true,
                        color = BazzarTheme.colors.bottomNavBarSelected
                    )
            }

            // show brands
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.s)
            ) {
                if (state.slider.size > 1) item {
                    IndicatorHomeImageSlider(imagePathList = state.slider.mapNotNull {
                        it.imagePath ?: ""
                    }, modifier = Modifier.wrapContentHeight(), onSliderClicked = {
                        onSendEvent(BazarDetailContract.Event.OnSliderClicked(it))
                    })
                }
                if (state.categoriesList.size > 1) item {
                    SubCategoryTextSlider(modifier = Modifier
                        .background(BazzarTheme.colors.white)
                        .fillMaxWidth()
                        .height(44.dp),
                        subCategoryList = state.categoriesList,
                        onClickSubCategory = { categoryIndex ->
                            onSendEvent(
                                BazarDetailContract.Event.OnSubCategoryClicked(categoryIndex)
                            )
                        })
                }
                item {
                    SortFilterBar(
                        numOfProducts = state.productList?.size,
                        numOfSelectedFilters = state.numOfSelectedFilter,
                        onFilterClicked = { onSendEvent(BazarDetailContract.Event.OnFilterClicked) },
                        onSortClicked = { onSendEvent(BazarDetailContract.Event.OnSortClicked) },
                    )
                }
                gridItems(count = state.productList.orEmpty().size,
                    nColumns = 2,
                    horizontalArrangement = Arrangement.Start,
                    itemContent = { index ->
                        val item = state.productList.orEmpty()[index]
                        ProductItem(
                            item,
                            modifier = Modifier.padding(BazzarTheme.spacing.xs),
                            onItemClicked = {
                                onSendEvent(BazarDetailContract.Event.OnProductClicked(index))
                            }, onFavClicked = {
                                onSendEvent(BazarDetailContract.Event.OnProductClicked(index))
                            }, onAddToCartClicked = {
                                onSendEvent(
                                    BazarDetailContract.Event.OnProductAddToCartClicked(
                                        index
                                    )
                                )

                            })
                        val isLastItem = index == state.productList.orEmpty().lastIndex
                        if (isLastItem && state.isLoadingMore.not() && state.hasMore) {
                            onSendEvent(BazarDetailContract.Event.ReachedListEnd)
                        }

                    })
                item {
                    Spacer(modifier = Modifier.height(BazzarTheme.spacing.s))
                }
            }
        }

        SuccessAddedToCart(
            modifier = Modifier.align(Alignment.BottomCenter),
            show = state.showSuccessAddedToCart,
            onContinueShoppingClick = { onSendEvent(BazarDetailContract.Event.OnContinueShoppingClicked) },
            onVisitCardClick = { onSendEvent(BazarDetailContract.Event.OnVisitYourCartClicked) },
        )


        SortDialog(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = BazzarTheme.spacing.m)
                .padding(bottom = BazzarTheme.spacing.m)
                .align(Alignment.BottomCenter),
            show = state.showSortDialog,
            sortingList = state.sortFilter?.sortingList ?: listOf(),
            selectedSort = state.selectedSort,
            onDismiss = { onSendEvent(BazarDetailContract.Event.OnDismissSortDialogClicked) },
            onSelectSortItem = { onSendEvent(BazarDetailContract.Event.OnSortItemSelected(it)) },
            onApply = { onSendEvent(BazarDetailContract.Event.OnApplySortClicked) }
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
            maxPrice = state.selectedFilterMaxPrice ?: 3000,
            onFilterTypeClick = { onSendEvent(BazarDetailContract.Event.OnFilterTypeClicked(it)) },
            onSelectUnselectFilter = { filter, isSelect ->
                onSendEvent(BazarDetailContract.Event.OnSelectUnselectFilter(filter, isSelect))
            },
            onApply = { onSendEvent(BazarDetailContract.Event.OnApplyFiltersClicked) },
            onRest = { onSendEvent(BazarDetailContract.Event.OnResetFiltersClicked) },
            onDismiss = { onSendEvent(BazarDetailContract.Event.OnDismissFilterDialogClicked) },
            onMinPriceChanged = { onSendEvent(BazarDetailContract.Event.OnMinPriceChanged(it)) },
            onMaxPriceChanged = { onSendEvent(BazarDetailContract.Event.OnMaxPriceChanged(it)) },
        )
    }

}









