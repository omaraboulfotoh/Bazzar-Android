package com.bazzar.android.presentation.category_screen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.android.model.home.Brand
import com.bazzar.android.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BrandGrid(
    brandList: List<Brand>,
    onBrandClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .height(800.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
        ) {
            itemsIndexed(brandList) { index, item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable { onBrandClicked(index) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GlideImage(
                        model = item.imagePath,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(90.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        contentDescription = null,
                    )
                    Text(
                        text = item.title ?: "",
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

