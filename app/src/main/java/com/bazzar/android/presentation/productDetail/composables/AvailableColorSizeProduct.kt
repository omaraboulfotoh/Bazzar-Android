package com.bazzar.android.presentation.productDetail.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.Caption
import com.bazzar.android.presentation.composables.RemoteImage
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.theme.Shapes

@Composable
fun AvailableColorSizeProduct(
    itemImages: List<String> = listOf(),
    sizeList: List<String> = listOf(),
    onColorSelected: (Int) -> Unit,
    onSizeSelected: (Int) -> Unit,
    isColorItemClicked: Boolean = false,
    isSizeClicked: Boolean = false,
) {

    val stateImages = rememberScrollState()
    val stateSizes = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .defaultMinSize(minHeight = 180.dp)
            .background(BazzarTheme.colors.white)
            .padding(horizontal = BazzarTheme.spacing.m, vertical = BazzarTheme.spacing.xs),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = spacedBy(BazzarTheme.spacing.m)
    ) {
        Text(
            text = stringResource(id = R.string.availables),
            style = BazzarTheme.typography.body2Bold,
            color = BazzarTheme.colors.black,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .horizontalScroll(stateSizes),
            horizontalArrangement = spacedBy(BazzarTheme.spacing.m),
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemImages.forEachIndexed { index, item ->
                Card(
                    modifier = Modifier
                        .size(64.dp)
                        .clickable { onColorSelected(index) },
                    shape = Shapes.medium,
                    border = BorderStroke(
                        1.dp,
                        color = (colorResource(id = if (isColorItemClicked) R.color.prussian_blue else R.color.light_gray)),
                    )
                ) {
                    RemoteImage(
                        imageUrl = item,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(BazzarTheme.spacing.xs),
                        background = BazzarTheme.colors.white,
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .horizontalScroll(stateSizes),
            horizontalArrangement = spacedBy(BazzarTheme.spacing.m),
            verticalAlignment = Alignment.CenterVertically
        ) {
            sizeList.forEachIndexed { index, sizeTitle ->
                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .height(32.dp)
                        .clickable { onColorSelected(index) },
                    shape = Shapes.medium,
                    border = BorderStroke(
                        1.dp,
                        color = (colorResource(id = if (isColorItemClicked) R.color.prussian_blue else R.color.light_gray)),
                    )
                ) {
                    Caption(
                        text = sizeTitle,
                        isBold = true,
                        color = (colorResource(id = if (isSizeClicked) R.color.prussian_blue else R.color.light_gray))
                    )
                }
            }
        }
    }
}