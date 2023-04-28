package com.bazzar.android.presentation.productDetail.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.RemoteImage

@Composable
fun AvailableColorSizeProduct(
    itemImages: List<String> = listOf(),
    sizeList: List<String> = listOf(),
    onColorSelected: (Int) -> Unit,
    onSizeSelected: (Int) -> Unit,
    isColorItemClicked: Boolean = false,
    isSizeClicked: Boolean = false,
) {
    Column(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .height(180.dp)
            .background(Color.White)
            .padding(start = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row {
            Text(
                text = stringResource(id = R.string.availables),
                style = MaterialTheme.typography.subtitle2.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    color = Color.Black
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            itemImages.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .border(
                            1.dp,
                            color = (colorResource(id = if (isColorItemClicked) R.color.prussian_blue else R.color.light_gray)),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable { onColorSelected(index) }
                ) {
                    RemoteImage(
                        imageUrl = item,
                        description = null,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            sizeList.forEachIndexed { index, sizeTitle ->
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .border(
                            1.dp,
                            color = (colorResource(id = if (isSizeClicked) R.color.prussian_blue else R.color.light_gray)),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clickable { onSizeSelected(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = sizeTitle,
                        color = (colorResource(id = if (isSizeClicked) R.color.prussian_blue else R.color.light_gray))
                    )
                }
            }
        }
    }
}