package com.bazzar.android.presentation.categoryScreen.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.model.home.Category
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.RemoteImageCard
import com.bazzar.android.presentation.theme.BazzarTheme

@SuppressLint("UnrememberedMutableState")
fun LazyListScope.CategoryList(
    categoryList: List<Category>,
    subCategoryList: List<Category>,
    onCategoryItemClicked: (Int) -> Unit,
    onDismissClicked: () -> Unit,
    onSubCategoryItemClicked: (Int) -> Unit,
) {
    itemsIndexed(
        items = categoryList
    ) { index, category ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(if (category.isSelected) 510.dp else 118.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = if (category.isSelected) R.color.prussian_blue else R.color.white)),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            border = BorderStroke(
                width = 1.dp,
                color = if (category.isSelected) BazzarTheme.colors.bottomNavBarSelected else BazzarTheme.colors.stroke
            )
        ) {
            if (category.isSelected.not()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(115.dp)
                        .padding(BazzarTheme.spacing.xs)
                        .clickable { onCategoryItemClicked(index) },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.s)
                ) {
                    RemoteImageCard(
                        imageUrl = category.imagePath,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    )
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.s)
                    ) {
                        Text(
                            text = category.title.orEmpty(),
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.subtitle2.copy(
                                fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                                color = colorResource(id = R.color.prussian_blue)
                            )
                        )
                        IconButton(
                            onClick = { onCategoryItemClicked(index) }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_down_arrow),
                                contentDescription = null,
                                tint = BazzarTheme.colors.indicatorActiveColor
                            )
                        }
                    }

                }
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = BazzarTheme.spacing.s)
                    ) {
                        RemoteImageCard(
                            imageUrl = category.imagePath,
                            withShimmer = false,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .width(120.dp)
                                .height(75.dp)
                                .align(Alignment.Center)
                                .clip(RoundedCornerShape(30.dp)),
                        )
                        IconButton(modifier = Modifier.align(Alignment.CenterEnd),
                            onClick = { onDismissClicked() }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_up),
                                contentDescription = null,
                            )
                        }
                    }
                    Text(
                        text = category.title.orEmpty(),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.subtitle2.copy(
                            fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                            color = colorResource(id = R.color.white),
                        )
                    )
                    // space
                    Spacer(modifier = Modifier.height(BazzarTheme.spacing.xxs))
                    // sub list
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2), modifier = Modifier.wrapContentHeight()
                    ) {
                        itemsIndexed(subCategoryList) { index, item ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onSubCategoryItemClicked(index) },
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                RemoteImageCard(
                                    imageUrl = item.imagePath,
                                    withShimmer = true,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .width(160.dp)
                                        .height(100.dp)
                                        .clip(RoundedCornerShape(15.dp))
                                        .border(width = 1.dp, color = BazzarTheme.colors.white),
                                )
                                Text(
                                    text = item.title ?: "",
                                    style = MaterialTheme.typography.overline.copy(
                                        fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                                        color = colorResource(id = R.color.white),
                                    ),
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(BazzarTheme.spacing.xxs))
                }
            }
        }
    }
}