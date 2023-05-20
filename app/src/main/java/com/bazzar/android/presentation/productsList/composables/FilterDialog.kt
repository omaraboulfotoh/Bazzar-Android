package com.bazzar.android.presentation.productsList.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.model.home.Filter
import com.bazzar.android.R
import com.bazzar.android.common.advancedShadow
import com.bazzar.android.presentation.productsList.composables.filter.FilterSelectionList
import com.bazzar.android.presentation.productsList.composables.filter.FilterType
import com.bazzar.android.presentation.productsList.composables.filter.FilterTypes
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun FilterDialog(
    modifier: Modifier = Modifier,
    show: Boolean = false,
    selectedFilterType: FilterType?,
    numOfSelectedCategoryFilters: Int = 0,
    numOfSelectedBrandFilters: Int = 0,
    numOfSelectedColorFilters: Int = 0,
    numOfSelectedSizeFilters: Int = 0,
    selectedFiltersToShow: List<Filter>?,
    minPrice: Int = 0,
    maxPrice: Int = 1000,
    onFilterTypeClick: (filterType: FilterType) -> Unit,
    onSelectUnselectFilter: (filter: Filter, isSelect: Boolean) -> Unit,
    onMinPriceChanged: (minPrice: Int) -> Unit,
    onMaxPriceChanged: (maxPrint: Int) -> Unit,
    onApply: () -> Unit,
    onRest: () -> Unit,
    onDismiss: () -> Unit,
) {
    if (show.not()) return

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = BazzarTheme.colors.backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 32.dp),
        shape = RoundedCornerShape(24.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = BazzarTheme.spacing.m)
                .padding(bottom = BazzarTheme.spacing.m),
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                IconButton(
                    modifier = Modifier.align(Alignment.End), onClick = onDismiss
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close_circular),
                        modifier = Modifier.size(18.dp),
                        tint = colorResource(id = R.color.prussian_blue),
                        contentDescription = ""
                    )
                }

                Text(
                    text = stringResource(id = R.string.sorting),
                    style = BazzarTheme.typography.subtitle1Bold,
                    color = colorResource(id = R.color.prussian_blue)
                )

                Row(modifier = Modifier.fillMaxSize()) {
                    FilterTypes(
                        selectedFilterType = selectedFilterType,
                        numOfSelectedCategoryFilters = numOfSelectedCategoryFilters,
                        numOfSelectedBrandFilters = numOfSelectedBrandFilters,
                        numOfSelectedColorFilters = numOfSelectedColorFilters,
                        numOfSelectedSizeFilters = numOfSelectedSizeFilters,
                        onFilterTypeClick = onFilterTypeClick
                    )
                    FilterSelectionList(
                        modifier = modifier
                            .padding(top = 32.dp)
                            .weight(1f),
                        filterList = selectedFiltersToShow,
                        filterType = selectedFilterType,
                        onSelectUnselectFilter = onSelectUnselectFilter,
                        onMinPriceChanged = onMinPriceChanged,
                        onMaxPriceChanged = onMaxPriceChanged,
                        minPrice = minPrice,
                        maxPrice = maxPrice,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Button(
                    modifier = Modifier
                        .advancedShadow(
                            cornersRadius = 33.dp,
                            shadowBlurRadius = 15.dp,
                            alpha = 0.5f,
                            offsetX = 5.dp,
                            offsetY = 5.dp,
                        )
                        .clip(RoundedCornerShape(33.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BazzarTheme.colors.primaryButtonColor
                    ),
                    onClick = onApply
                ) {
                    Text(
                        modifier = Modifier.padding(BazzarTheme.spacing.s),
                        text = stringResource(id = R.string.apply),
                        style = BazzarTheme.typography.body2Bold,
                        color = BazzarTheme.colors.white,
                        fontSize = 14.sp,
                    )
                }

                Button(
                    modifier = Modifier
                        .background(
                            color = colorResource(id = R.color.white),
                            shape = RoundedCornerShape(33.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.prussian_blue),
                            shape = RoundedCornerShape(33.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = BazzarTheme.colors.white),
                    onClick = onRest
                ) {
                    Text(
                        modifier = Modifier.padding(BazzarTheme.spacing.s),
                        text = stringResource(id = R.string.reset),
                        style = BazzarTheme.typography.body2Bold,
                        color = colorResource(id = R.color.black),
                        fontSize = 14.sp,
                    )
                }
            }
        }

    }
}