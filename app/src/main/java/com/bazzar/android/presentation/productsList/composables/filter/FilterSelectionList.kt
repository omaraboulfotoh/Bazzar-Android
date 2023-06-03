package com.bazzar.android.presentation.productsList.composables.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RangeSlider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.model.home.Filter
import com.bazzar.android.R
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.composables.TextInputField
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun FilterSelectionList(
    modifier: Modifier = Modifier,
    filterType: FilterType?,
    filterList: List<Filter>? = null,
    minPrice: Int = 0,
    maxPrice: Int = 3000,
    onSelectUnselectFilter: (filter: Filter, isSelect: Boolean) -> Unit,
    onMinPriceChanged: (minPrice: Int) -> Unit,
    onMaxPriceChanged: (maxPrint: Int) -> Unit,
) {

    if (filterType == null) return

    Column(modifier = modifier) {
        if (filterType == FilterType.FILTER_PRICE) {
            PriceSelection(
                minPrice = minPrice,
                maxPrice = maxPrice,
                onMinPriceChanged = onMinPriceChanged,
                onMaxPriceChanged = onMaxPriceChanged,
            )
        } else {
            SelectionList(
                modifier = Modifier.padding(top = 32.dp),
                withSearch = filterType == FilterType.FILTER_CATEGORY || filterType == FilterType.FILTER_BRAND,
                filterList = filterList,
                onSelectUnselectFilter = onSelectUnselectFilter
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriceSelection(
    minPrice: Int = 0,
    maxPrice: Int = 3000,
    onMinPriceChanged: (minPrice: Int) -> Unit,
    onMaxPriceChanged: (maxPrint: Int) -> Unit,
) {
    var sliderValues by remember { mutableStateOf(minPrice.toFloat()..maxPrice.toFloat()) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(start = 56.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextInputField(
                modifier = Modifier
                    .width(68.dp)
                    .height(48.dp),
                text = "$maxPrice",
                placeholder = "",
                textStyle = BazzarTheme.typography.subtitle1SemiBold.copy(textAlign = TextAlign.Center),
                textColor = colorResource(id = R.color.black),
                backgroundColor = colorResource(id = R.color.white),
                keyboardType = KeyboardType.Number,
                onValueChange = { onMaxPriceChanged.invoke(it.toIntOrNull().orZero()) }
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "Highest Price",
                style = BazzarTheme.typography.subtitle3SemiBold,
                color = colorResource(id = R.color.prussian_blue)
            )
        }
        RangeSlider(
            modifier = Modifier
                .padding(vertical = 98.dp)
                .width(248.dp)
                .rotate(-90f),
            value = sliderValues,
            onValueChange = { sliderValues_ ->
                sliderValues = sliderValues_
            },
            valueRange = 0f..3000f,
            onValueChangeFinished = {
                // this is called when the user completed selecting the value
                onMinPriceChanged.invoke(sliderValues.start.toInt())
                onMaxPriceChanged.invoke(sliderValues.endInclusive.toInt())
            },
            steps = 3000
        )
        Row(
            modifier = Modifier.padding(start = 56.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextInputField(
                modifier = Modifier
                    .width(68.dp)
                    .height(48.dp),
                text = "$minPrice",
                placeholder = "",
                textStyle = BazzarTheme.typography.subtitle1SemiBold.copy(textAlign = TextAlign.Center),
                textColor = colorResource(id = R.color.black),
                backgroundColor = colorResource(id = R.color.white),
                keyboardType = KeyboardType.Number,
                onValueChange = { onMinPriceChanged.invoke(it.toIntOrNull().orZero()) }
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "Lowest Price",
                style = BazzarTheme.typography.subtitle3SemiBold,
                color = colorResource(id = R.color.prussian_blue)
            )
        }

        Text(text = "Start: ${sliderValues.start}, End: ${sliderValues.endInclusive}")
    }
}

@Composable
fun SelectionList(
    modifier: Modifier = Modifier,
    withSearch: Boolean = false,
    filterList: List<Filter>?,
    onSelectUnselectFilter: (filter: Filter, isSelect: Boolean) -> Unit,
) {
    if (filterList.isNullOrEmpty()) return

    Column(
        modifier = modifier
    ) {
//        if (withSearch) {
//            SearchTextInput(modifier = Modifier
//                .padding(start = 8.dp, end = 4.dp)
//                .fillMaxWidth()
//                .border(
//                    width = 1.dp,
//                    color = BazzarTheme.colors.stroke,
//                    shape = RoundedCornerShape(24.dp)
//                )
//                .background(
//                    color = BazzarTheme.colors.white, shape = RoundedCornerShape(24.dp)
//                ),
//                hint = stringResource(id = R.string.search_location),
////                value = searchTerm ?: "",
//                value = "",
//                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
//                keyboardActions = KeyboardActions(onSearch = { }),
//                childRowModifier = Modifier.padding(vertical = BazzarTheme.spacing.spacerMini),
//                onValueChange = { },
//                textStyle = BazzarTheme.typography.overlineBold.copy(color = BazzarTheme.colors.white),
//                cursorColor = SolidColor(BazzarTheme.colors.white),
//                leadingIcon = {
//                    Icon(
//                        modifier = Modifier.padding(horizontal = BazzarTheme.spacing.s),
//                        painter = painterResource(id = R.drawable.search_icon),
//                        tint = if ("".isNullOrEmpty()) BazzarTheme.colors.white else Color.Transparent,
//                        contentDescription = ""
//                    )
//                })
//        }

//        filterList.forEach { filter ->
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 16.dp, start = 16.dp),
//                verticalAlignment = Alignment.CenterVertically,
//            ) {
//                Checkbox(
//                    modifier = Modifier.size(16.dp),
//                    checked = filter.isSelected, onCheckedChange = {
//                        onSelectUnselectFilter.invoke(filter, it)
//                    }, colors = CheckboxDefaults.colors(
//                        checkedColor = colorResource(id = R.color.deep_sky_blue)
//                    )
//                )
//                Text(
//                    modifier = Modifier.padding(start = 8.dp),
//                    text = filter.title.orEmpty(),
//                    style = BazzarTheme.typography.captionSemiBold,
//                    color = colorResource(id = R.color.black)
//                )
//            }
//        }

        LazyColumn(
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
        ) {
            items(filterList) { filter ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        modifier = Modifier.size(16.dp),
                        checked = filter.isSelected, onCheckedChange = {
                            onSelectUnselectFilter.invoke(filter, it)
                        }, colors = CheckboxDefaults.colors(
                            checkedColor = colorResource(id = R.color.deep_sky_blue)
                        )
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = filter.title.orEmpty(),
                        style = BazzarTheme.typography.captionSemiBold,
                        color = colorResource(id = R.color.black)
                    )
                }
            }
        }
    }
}