package com.bazzar.android.presentation.productsList.composables.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun FilterTypes(
    modifier: Modifier = Modifier,
    numOfSelectedCategoryFilters: Int = 0,
    numOfSelectedBrandFilters: Int = 0,
    numOfSelectedColorFilters: Int = 0,
    numOfSelectedSizeFilters: Int = 0,
    selectedFilterType: FilterType?,
    showCategory :Boolean = false,
    showBrands:Boolean = false,
    onFilterTypeClick: (filterType: FilterType) -> Unit
) {
    Column(
        modifier = modifier
            .width(144.dp)
            .fillMaxHeight()
            .padding(top = BazzarTheme.spacing.m)
            .clip(RoundedCornerShape(22.dp))
            .background(color = BazzarTheme.colors.white)
    ) {
        FilterTypeItem(
            title = stringResource(id = R.string.filter_price),
            isSelected = selectedFilterType == FilterType.FILTER_PRICE,
            onFilterTypeClick = { onFilterTypeClick(FilterType.FILTER_PRICE) }
        )
        if(showCategory)
        FilterTypeItem(
            title = stringResource(id = R.string.filter_category),
            isSelected = selectedFilterType == FilterType.FILTER_CATEGORY,
            numOfSelectedFilters = numOfSelectedCategoryFilters,
            onFilterTypeClick = { onFilterTypeClick(FilterType.FILTER_CATEGORY) }
        )
        if(showBrands)
        FilterTypeItem(
            title = stringResource(id = R.string.filter_brand),
            isSelected = selectedFilterType == FilterType.FILTER_BRAND,
            numOfSelectedFilters = numOfSelectedBrandFilters,
            onFilterTypeClick = { onFilterTypeClick(FilterType.FILTER_BRAND) }
        )
        FilterTypeItem(
            title = stringResource(id = R.string.filter_color),
            isSelected = selectedFilterType == FilterType.FILTER_COLOR,
            numOfSelectedFilters = numOfSelectedColorFilters,
            onFilterTypeClick = { onFilterTypeClick(FilterType.FILTER_COLOR) }
        )
        FilterTypeItem(
            title = stringResource(id = R.string.filter_size),
            isSelected = selectedFilterType == FilterType.FILTER_SIZE,
            numOfSelectedFilters = numOfSelectedSizeFilters,
            onFilterTypeClick = { onFilterTypeClick(FilterType.FILTER_SIZE) }
        )
    }
}