package com.bazzar.android.presentation.productsList.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.model.home.Category
import com.bazzar.android.presentation.composables.OverLine
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.theme.Shapes
import com.bazzar.android.presentation.theme.Shapes_MediumX

@Composable
fun SubCategoryTextSlider(
    subCategoryList: List<Category>,
    onClickSubCategory: (Int) -> Unit,
    modifier: Modifier
) {
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        itemsIndexed(subCategoryList) { index, category ->
            Card(
                Modifier
                    .wrapContentSize()
                    .defaultMinSize(32.dp, 32.dp)
                    .height(32.dp)
                    .clickable {
                        onClickSubCategory(index)
                    },
                shape = Shapes.medium,
                backgroundColor = if (category.isSelected) {
                    BazzarTheme.colors.primaryButtonColor
                } else {
                    BazzarTheme.colors.white
                },
                border = BorderStroke(
                    width = 0.5.dp,
                    color = if (category.isSelected) {
                        BazzarTheme.colors.primaryButtonColor
                    } else {
                        BazzarTheme.colors.primaryButtonTextColorDisabled
                    }
                ),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(BazzarTheme.spacing.xs)
                ) {
                    OverLine(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.Center),
                        text = category.title.orEmpty(),
                        color = if (category.isSelected) BazzarTheme.colors.white
                        else BazzarTheme.colors.primaryButtonColor,
                        maxLines = 2,
                        textAlign = TextAlign.Center,
                        style = BazzarTheme.typography.overlineBold
                    )
                }
            }
        }
    }
}
