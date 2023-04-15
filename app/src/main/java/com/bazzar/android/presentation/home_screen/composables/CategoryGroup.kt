package com.bazzar.android.presentation.home_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.android.model.home.Category
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.HeaderTextWithViewAll
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CategoryGroup(categoryList: List<Category>?) {
    HeaderTextWithViewAll(text = stringResource(id = R.string.home_featured_category))
    Box(
        Modifier
            .fillMaxWidth()
            .height(500.dp)
    ) {
        LazyVerticalGrid(
            GridCells.Fixed(2), contentPadding = PaddingValues(16.dp),
        ) {
            categoryList?.size?.let {
                items(it) { index ->
                    val category = categoryList[index]
                    Box(
                        Modifier
                            .padding(top = 16.dp)
                            .height(216.dp)
                            .wrapContentWidth()
                    ) {
                        Card(
                            modifier = Modifier
                                .size(168.dp)
                                .align(Alignment.BottomCenter),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
                            content = {}
                        )
                        GlideImage(
                            model = category.imagePath,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .size(120.dp)
                        )
                        Text(
                            text = category.title ?: "",
                            modifier = Modifier
                                .padding(bottom = 46.dp)
                                .align(Alignment.BottomCenter),
                            style = MaterialTheme.typography.subtitle2.copy(
                                fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                                color = colorResource(id = R.color.black)
                            )
                        )

                    }

                }
            }
        }
    }
}
