package com.bazzar.android.presentation.homeScreen.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.android.model.home.BazaarModel
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.CustomLazyRow
import com.bazzar.android.presentation.composables.HeaderTextWithViewAll
import com.bazzar.android.presentation.composables.SemiCircleImageView
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun FeaturedBazaarSlider(
    slides2: List<BazaarModel>,
    onShowAllClicked: () -> Unit,
    onBazaarClicked: (Int) -> Unit,
) {
    HeaderTextWithViewAll(
        text = stringResource(id = R.string.home_screen_featured_bzarz),
        onShowAllClicked = onShowAllClicked
    )
    Spacer(modifier = Modifier.height(BazzarTheme.spacing.m))
    CustomLazyRow(
        imageList = slides2,
        customIV = { imagePath, text, modifier ->
            Column {
                SemiCircleImageView(
                    imagePath = imagePath,
                    text = text,
                    modifier = modifier
                )
            }
        },
        spaceBetweenItems = BazzarTheme.spacing.m,
        onClick = onBazaarClicked
    )
}
