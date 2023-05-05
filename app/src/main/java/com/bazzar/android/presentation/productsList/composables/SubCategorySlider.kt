package com.bazzar.android.presentation.productsList.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.model.home.Category
import com.bazzar.android.presentation.composables.OverLine
import com.bazzar.android.presentation.composables.RemoteImageCard
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun SubCategorySlider(
    subCategoryList: List<Category>, onClickSubCategory: (Int) -> Unit, modifier: Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        itemsIndexed(subCategoryList) { index, category ->
            Column(
                Modifier
                    .width(96.dp)
                    .fillMaxHeight()
                    .clickable { onClickSubCategory(index) },
                verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.s),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    Modifier
                        .width(90.dp)
                        .height(53.dp),
                    shape = RoundedCornerShape(30.dp),
                    border = BorderStroke(
                        width = 2.dp,
                        color = if (category.isSelected) {
                            BazzarTheme.colors.primaryButtonColor
                        } else {
                            BazzarTheme.colors.primaryButtonTextColorDisabled
                        }
                    ),
                ) {
                    RemoteImageCard(
                        imageUrl = category.imagePath,
                        contentScale = if (category.isSelected) ContentScale.Crop else ContentScale.Inside,
                        modifier = Modifier
                            .fillMaxSize(),
                        background = BazzarTheme.colors.white
                    )
                }
                OverLine(
                    modifier = Modifier.fillMaxWidth(),
                    text = category.title.orEmpty(),
                    color = BazzarTheme.colors.primaryButtonColor,
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    style = BazzarTheme.typography.overlineBold
                )
            }
        }
    }
}
