package com.bazzar.android.presentation.category_screen.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.android.model.home.Category
import com.bazzar.android.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@SuppressLint("UnrememberedMutableState")
fun LazyListScope.CategoryList(
    categoryList: List<Category>,
    subCategoryList: List<Category>,
    onCategoryItemClicked: (Int) -> Unit,
    onSubCategoryItemClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    itemsIndexed(
        items = categoryList
    ) { index, category ->
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Box(modifier = Modifier
                .width(343.dp)
                .height(if (category.isSelected) 508.dp else 115.dp)
                .align(Alignment.Center)
                .clickable { onCategoryItemClicked(index) }
                .clip(RoundedCornerShape(20.dp))
                .background(if (category.isSelected) colorResource(id = R.color.prussian_blue) else Color.White)) {
                if (category.isSelected.not()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        GlideImage(
                            model = category.imagePath,
                            modifier = Modifier
                                .width(160.dp)
                                .height(100.dp)
                                .align(Alignment.CenterVertically)
                                .padding(start = 8.dp),
                            contentDescription = null
                        )
                        androidx.compose.material3.Text(
                            text = category.title ?: "",
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .align(Alignment.CenterVertically),
                            style = MaterialTheme.typography.subtitle2.copy(
                                fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                                color = colorResource(id = R.color.prussian_blue)
                            )
                        )
                        IconButton(
                            onClick = {}, modifier = Modifier.padding(start = 15.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_down_arrow),
                                contentDescription = null,
                                tint = colorResource(id = R.color.prussian_blue)
                            )
                        }
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        GlideImage(
                            model = category.imagePath,
                            modifier = Modifier
                                .width(120.dp)
                                .height(75.dp)
                                .align(Alignment.CenterHorizontally)
                                .padding(start = 8.dp)
                                .clip(RoundedCornerShape(30.dp)),
                            contentDescription = null,
                        )
                        androidx.compose.material3.Text(
                            text = category.title ?: "",
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.subtitle2.copy(
                                fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                                color = colorResource(id = R.color.white)
                            )
                        )
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp),
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            items(
                                subCategoryList.size ?: return@LazyVerticalGrid
                            ) { index ->
                                val image = subCategoryList[index]
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    GlideImage(
                                        model = image.imagePath,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .width(160.dp)
                                            .height(100.dp)
                                            .clip(RoundedCornerShape(15.dp)),
                                        contentDescription = null,
                                    )
                                    Text(
                                        text = image.title ?: "",
                                        style = MaterialTheme.typography.overline.copy(
                                            fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                                            color = colorResource(id = R.color.white),
                                        ),
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}