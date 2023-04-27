package com.bazzar.android.presentation.homeScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.IndicatorImageSlider
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.homeScreen.HomeContract
import com.bazzar.android.presentation.theme.BazzarTheme


@Composable
fun HomeScreenContent(state: HomeContract.State, onSendEvent: (HomeContract.Event) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .background(BazzarTheme.colors.backgroundColor)
            .fillMaxSize()
            .padding(bottom = BottomNavigationHeight),
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m),
        horizontalAlignment = Alignment.Start,
    ) {
        item {
            HomeHeader(onSearchClicked = {

            })
        }
        item {
            IndicatorImageSlider(imagePathList = state.slides1.orEmpty()
                .mapNotNull { it.imagePath ?: "" },
                modifier = Modifier.wrapContentHeight(),
                onSliderClicked = {
                    onSendEvent(HomeContract.Event.OnSliderClicked(0, it))
                })
        }
        if (state.categoryItems.isNullOrEmpty().not()) item {
            ProductsGroup(
                productsList = state.categoryItems,
                headerTitle = stringResource(id = R.string.home_screen_products_group),
                onProductClicked = { itemId ->
                    HomeContract.Event.OnProductClicked(itemId)
                }

            )
        }
        if (state.featuredCategories.isNullOrEmpty().not())
            CategoryGroup(state.featuredCategories.orEmpty(), onCategoryClicked = {

            })
        item {
            IndicatorImageSlider(
                state.slides2.orEmpty().mapNotNull { it.imagePath ?: "" },
                modifier = Modifier.wrapContentHeight(),
                onSliderClicked = {
                    onSendEvent(HomeContract.Event.OnSliderClicked(1, it))
                })
        }
        if (state.featuredBrands.isNullOrEmpty().not())
            FeaturedBrands(state.featuredBrands.orEmpty(), onBrandClicked = {

            })

        item {
            Spacer(modifier = Modifier.height(BazzarTheme.spacing.xs))
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreenContent() {
    HomeScreenContent(state = HomeContract.State(), onSendEvent = {})
}