package com.bazzar.android.presentation.productDetail.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
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
import com.android.model.home.ItemDetail
import com.android.model.home.ItemImages
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.Caption
import com.bazzar.android.presentation.composables.RemoteImage
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.theme.Shapes

@Composable
fun AvailableColorSizeProduct(
    itemColors: List<ItemImages> = listOf(),
    sizeList: List<ItemDetail> = listOf(),
    selectedDetail: ItemDetail?,
    onColorSelected: (Int) -> Unit,
    onSizeSelected: (Int) -> Unit,
) {

    val stateImages = rememberScrollState()
    val stateSizes = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
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
                .horizontalScroll(stateImages),
            horizontalArrangement = spacedBy(BazzarTheme.spacing.m),
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemColors.forEachIndexed { index, item ->
                val modifier =
                    if (item.imagePath.isNullOrEmpty().not()) Modifier.size(64.dp) else
                        Modifier
                            .wrapContentWidth()
                            .height(64.dp)

                Box(modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        onColorSelected(index)
                    }) {
                    Card(
                        modifier = modifier,
                        shape = Shapes.medium,
                        border = BorderStroke(
                            1.dp,
                            color = (colorResource(id = if (selectedDetail?.colorId == item.colorId) R.color.prussian_blue else R.color.light_gray)),
                        )
                    ) {
                        if (item.imagePath.isNullOrEmpty().not()) {
                            RemoteImage(
                                imageUrl = item.imagePath,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(BazzarTheme.spacing.xs),
                                background = BazzarTheme.colors.white,
                                contentScale = ContentScale.Fit
                            )
                        } else {
                            Caption(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = BazzarTheme.spacing.xxs),
                                text = item.colorTitle.orEmpty(),
                                isBold = true,
                                textAlign = TextAlign.Center,
                                color = (colorResource(id = if (selectedDetail?.colorId == item.colorId) R.color.prussian_blue else R.color.light_gray))
                            )
                        }
                    }
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
            sizeList.forEachIndexed { index, item ->
                Box(modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        onSizeSelected(index)
                    }) {
                    Card(
                        modifier = Modifier
                            .wrapContentSize()
                            .defaultMinSize(minHeight = 24.dp, minWidth = 48.dp),
                        shape = Shapes.medium,
                        border = BorderStroke(
                            1.dp,
                            color = (colorResource(id = if (selectedDetail?.id == item.id) R.color.prussian_blue else R.color.light_gray)),
                        )
                    ) {
                        Caption(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = BazzarTheme.spacing.xxs),
                            text = item.sizeTitle.orEmpty(),
                            isBold = true,
                            textAlign = TextAlign.Center,
                            color = (colorResource(id = if (selectedDetail?.id == item.id) R.color.prussian_blue else R.color.light_gray))
                        )
                    }
                }
            }
        }
    }
}