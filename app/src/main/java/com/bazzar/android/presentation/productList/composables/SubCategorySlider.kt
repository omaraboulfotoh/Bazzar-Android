package com.bazzar.android.presentation.productList.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.android.model.home.Category
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.RemoteImage

@Composable
fun SubCategorySlider(
    subCategoryList: List<Category>?,
    onClickSubCategory: (Int) -> Unit,
    modifier: Modifier
) {
    Box(modifier) {
        LazyRow(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentWidth()
                .height(43.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            subCategoryList?.let {
                items(it) { category ->
                    Box(
                        Modifier
                            .width(96.dp)
                            .height(93.dp)
                            .clickable { onClickSubCategory(category.id ?: return@clickable) }
//                            .background(colorResource(id = if (isSubCategoryClicked) R.color.prussian_blue else R.color.white)),
                    ) {
                        Box(
                            Modifier
                                .width(90.dp)
                                .height(53.dp)
                                .clip(RoundedCornerShape(26.5.dp))
                                .border(
                                    1.dp,
                                    colorResource(id = if (category.isSelected) R.color.prussian_blue else R.color.Gray55),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .background(colorResource(id = if (category.isSelected) R.color.prussian_blue else R.color.white)),
                        ) {
                            RemoteImage(
                                imageUrl = category.imagePath,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(53.dp)
                            )
                        }
                        Text(
                            text = category.title ?: "",
                            style = MaterialTheme.typography.overline.copy(
                                fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                                color = if (category.isSelected) colorResource(id = R.color.black) else colorResource(
                                    id = R.color.Gray55
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}
