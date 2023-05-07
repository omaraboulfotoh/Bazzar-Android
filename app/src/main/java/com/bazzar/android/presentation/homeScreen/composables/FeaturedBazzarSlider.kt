package com.bazzar.android.presentation.homeScreen.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.model.home.BzarzModel
import com.android.model.home.HomeSlider
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.CustomLazyRow
import com.bazzar.android.presentation.composables.HeaderTextWithViewAll
import com.bazzar.android.presentation.composables.SemiCircleImageView

@Composable
fun FeaturedBazzarSlider(slides2: List<BzarzModel>?) {
    HeaderTextWithViewAll(text = stringResource(id = R.string.home_screen_featured_bzarz))
    CustomLazyRow(
        imageList = slides2,
        customIV = { imagePath, text ->
            Column {
                SemiCircleImageView(
                    imagePath = imagePath,
                    text = text
                )
            }
        },
        topPadding = 16.dp,
        spaceBetweenItems = 16.dp
    )
}
