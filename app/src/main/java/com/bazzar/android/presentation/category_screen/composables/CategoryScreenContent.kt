package com.bazzar.android.presentation.category_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.android.model.home.Brand
import com.android.model.home.Category
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.RemoteImage

@Composable
fun CategoryScreenContent(
    state: CategoryContract.State,
    onSendEvent: (CategoryContract.Event) -> Unit
) {
    var isCategory = state.showCategories
    var searchClicked by remember { mutableStateOf(true) }
    val categoryList = state.categoryList
    val brandList = state.brandList
    val subCategoryList = state.subCategoriesList
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white_smoke)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            BrandCategoryHeader(isCategory)
        }
        item {
            ToggleBrandCategory({ onSendEvent(CategoryContract.Event.OnToggleClicked) }, isCategory)
        }
        item {
            Search(isCategory = isCategory, searchClicked = searchClicked, onSearchClick = {
                onSendEvent(CategoryContract.Event.OnSearchClicked)
            })
        }
        item {
            CategoryList(isCategory, categoryList, subCategoryList, onCategoryItemClicked = {
                onSendEvent(CategoryContract.Event.OnCategoryItemClicked(it))
            }) { onSendEvent(CategoryContract.Event.OnSubCategoryItemClicked(it)) }
        }
        item {
            BrandGrid(
                isCategory = isCategory,
                brandList,
                onBrandClicked = { onSendEvent(CategoryContract.Event.OnBrandItemClicked(it)) })
        }
    }
}


@Composable
fun ToggleBrandCategory(onToggle: (Boolean) -> Unit, isCategory: Boolean) {
    IconButton(onClick = {
        onToggle(isCategory)
    }) {
        Box(
            Modifier
                .width(if (!isCategory) 104.dp else 84.dp)
                .height(28.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(colorResource(id = R.color.titan_white)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.toggle_circle),
                contentDescription = null,
                tint = colorResource(id = R.color.deep_sky_blue),
                modifier = if (!isCategory) Modifier
                    .align(Alignment.CenterStart)
                    .padding(2.dp) else Modifier
                    .align(Alignment.CenterEnd)
                    .padding(2.dp)
            )
            Box(
                modifier = if (!isCategory) {
                    Modifier
                        .size(120.dp)
                        .align(Alignment.Center)
                        .padding(end = 5.dp)
                } else {
                    Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                        .padding(start = 5.dp)
                },
                contentAlignment = if (!isCategory) Alignment.CenterEnd else Alignment.CenterStart
            ) {
                Text(
                    text = stringResource(
                        id = if (!isCategory) R.string.category_category_toggle_title else R.string.category_brands_toggle_title
                    ), style = MaterialTheme.typography.caption.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                        color = colorResource(id = R.color.prussian_blue),
                    )
                )
            }
        }
    }
}

@Composable
fun BrandCategoryHeader(isCategory: Boolean) {
    Row(
        Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = {

            }, modifier = Modifier.padding(start = 16.dp)
        ) {
            if (!isCategory) {
                Icon(
                    painter = painterResource(R.drawable.ic_back), contentDescription = "back"
                )
            }
        }
        Box(
            Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(
                    id = if (isCategory) R.string.category_category_title else R.string.category_brands_title
                ), style = MaterialTheme.typography.subtitle1.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    color = colorResource(id = R.color.prussian_blue),
                ), modifier = Modifier.align(Alignment.Center)
            )
        }
        IconButton(
            onClick = {

            }, modifier = Modifier.padding(start = 16.dp)
        ) {
            if (isCategory) {
                Icon(
                    painter = painterResource(R.drawable.search_icon), contentDescription = "Search"
                )
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun CategoryList(
    isCategory: Boolean,
    categoryList: List<Category>?,
    subCategoryList: List<Category>?,
    onCategoryItemClicked: (Int) -> Unit,
    onSubCategoryItemClicked: (Int) -> Unit,
) {
    if (isCategory) {
        LazyColumn(
            modifier = Modifier
                .padding(top = 20.dp)
                .height(757.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            categoryList?.let {
                items(it.size) { index ->
                    val category = categoryList[index]
                    val isSubCategorySec = mutableStateOf(false)
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        IconButton(onClick = { onCategoryItemClicked(index) }) {
                            Box(
                                modifier = Modifier
                                    .width(343.dp)
                                    .height(if (isSubCategorySec.value) 508.dp else 115.dp)
                                    .align(Alignment.Center)
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(if (isSubCategorySec.value) colorResource(id = R.color.prussian_blue) else Color.White)
                            ) {
                                if (!isSubCategorySec.value) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        if (!isSubCategorySec.value) {
                                            RemoteImage(
                                                imageUrl = category.imagePath,
                                                modifier = Modifier
                                                    .width(160.dp)
                                                    .height(100.dp)
                                                    .align(Alignment.CenterVertically)
                                                    .padding(start = 8.dp)
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
                                                onClick = {
                                                    /*TODO*/
                                                },
                                                modifier = Modifier.padding(start = 15.dp)
                                            ) {
                                                Icon(
                                                    painter = painterResource(R.drawable.ic_down_arrow),
                                                    contentDescription = null,
                                                    tint = colorResource(id = R.color.prussian_blue)
                                                )
                                            }

                                        }
                                    }
                                } else {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        RemoteImage(
                                            imageUrl = category.imagePath,
                                            modifier = Modifier
                                                .width(120.dp)
                                                .height(75.dp)
                                                .align(Alignment.CenterHorizontally)
                                                .padding(start = 8.dp)
                                                .clip(RoundedCornerShape(30.dp))
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
                                                subCategoryList?.size ?: return@LazyVerticalGrid
                                            ) { index ->
                                                val image = subCategoryList[index]
                                                Column(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(8.dp),
                                                    horizontalAlignment = Alignment.CenterHorizontally
                                                ) {
                                                    RemoteImage(
                                                        imageUrl = image.imagePath,
                                                        contentScale = ContentScale.Crop,
                                                        modifier = Modifier
                                                            .width(160.dp)
                                                            .height(100.dp)
                                                            .clip(RoundedCornerShape(15.dp))
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
            }
        }
    }
}

@Composable
fun BrandGrid(isCategory: Boolean, brandList: List<Brand>?, onBrandClicked: (Int) -> Unit) {
    if (!isCategory) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .height(800.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(16.dp),
            ) {
                items(brandList?.size ?: return@LazyVerticalGrid) { index ->
                    val image = brandList[index]
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RemoteImage(
                            imageUrl = image.imagePath,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(90.dp)
                                .clip(RoundedCornerShape(10.dp))
                        )
                        Text(
                            text = image.title ?: "",
                            style = MaterialTheme.typography.overline.copy(
                                fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                                color = colorResource(id = R.color.black),
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Search(isCategory: Boolean, searchClicked: Boolean, onSearchClick: (Boolean) -> Unit) {
    if (!isCategory) {
        Box(
            modifier = Modifier
                .width(343.dp)
                .height(35.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(colorResource(id = if (searchClicked) R.color.white_smoke else R.color.white))
                .border(
                    1.dp,
                    colorResource(id = if (searchClicked) R.color.prussian_blue else R.color.white_smoke),
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            IconButton(
                onClick = { onSearchClick(searchClicked) },
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .weight(1f), contentAlignment = Alignment.Center
                    ) {
                        if (!searchClicked) {
                            Icon(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 104.dp),
                                painter = painterResource(id = R.drawable.search_icon),
                                contentDescription = null,
                                tint = colorResource(id = R.color.prussian_blue)
                            )
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 128.dp),
                                text = stringResource(id = R.string.brand_search_title),
                                style = MaterialTheme.typography.overline.copy(
                                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                                    color = colorResource(id = R.color.prussian_blue).copy(alpha = 0.4f),
                                ),
                            )
                        } else {
                            var searchText by remember { mutableStateOf("") }
                            TextField(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .width(280.dp)
                                    .height(50.dp)
                                    .padding(start = 33.dp),
                                value = searchText,
                                onValueChange = { inputText -> searchText = inputText },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = colorResource(id = if (!searchClicked) R.color.white else R.color.white_smoke)
                                ),
                                textStyle = MaterialTheme.typography.overline.copy(
                                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                                    color = colorResource(id = R.color.deep_sky_blue),
                                )
                            )
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 284.dp),
                                text = stringResource(id = R.string.brand_search_cancel),
                                style = MaterialTheme.typography.overline.copy(
                                    fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                                    color = colorResource(id = R.color.deep_sky_blue),
                                ),
                            )
                        }
                    }
                }
            }
        }
    }
}
