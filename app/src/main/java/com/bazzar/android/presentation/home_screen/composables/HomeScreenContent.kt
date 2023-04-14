package com.bazzar.android.presentation.home_screen.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.Product
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.FooterTabBar
import com.bazzar.android.presentation.composables.MenuBarItem
import com.bazzar.android.presentation.home_screen.HomeContract
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


@Composable
fun HomeScreenContent(state: HomeContract.State, onSendEvent: (HomeContract.Event) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .background(colorResource(id = R.color.white_smoke))
            .fillMaxSize()
            .clickable { onSendEvent(HomeContract.Event.OnSliderClicked(2)) },
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            HomeHeader()
            BazzarSlider(state.slides1)
            FeaturedBazzarSlider(state.slides2)
            FeaturedBrands(state.featuredBrands)
            ProductsGroup(state.categoryItems)
            CategoryGroup(state.featuredCategories)
            FooterTabBar(MenuBarItem.Home)
        }
    }
}
@Preview
@Composable
fun PreviewHomeScreenContent() {
    HomeScreenContent(state = HomeContract.State(), onSendEvent = {
    })
}