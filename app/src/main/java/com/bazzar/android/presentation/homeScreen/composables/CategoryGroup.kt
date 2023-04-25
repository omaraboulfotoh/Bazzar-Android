package com.bazzar.android.presentation.homeScreen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.android.model.home.Category
import com.bazzar.android.R
import com.bazzar.android.presentation.common.gridItems
import com.bazzar.android.presentation.composables.HeaderTextWithViewAll
import com.bazzar.android.presentation.composables.RemoteImage

fun LazyListScope.CategoryGroup(
    categoryList: List<Category>,
    onCategoryClicked: (Int) -> Unit
) {

    item {
        HeaderTextWithViewAll(text = stringResource(id = R.string.home_featured_category))
    }
    gridItems(
        count = categoryList.size,
        nColumns = 2,
        horizontalArrangement = Arrangement.Start,
        itemContent = {
            val category = categoryList[it]
            Box(
                Modifier
                    .padding(top = 16.dp)
                    .height(216.dp)
                    .wrapContentWidth()
                    .clickable {
                        onCategoryClicked(it)
                    }
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
                RemoteImage(
                    imageUrl = category.imagePath,
                    contentScale = ContentScale.Crop,
                    withShimmer = false,
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
    )
}
