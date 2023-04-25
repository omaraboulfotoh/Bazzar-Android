package com.bazzar.android.presentation.home_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.IndicatorImageSlider
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.home_screen.HomeContract


@Composable
fun HomeScreenContent(state: HomeContract.State, onSendEvent: (HomeContract.Event) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .background(colorResource(id = R.color.white_smoke))
            .fillMaxSize()
            .padding(bottom = BottomNavigationHeight),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            HomeHeader()
        }
        item {
            IndicatorImageSlider(
                imagePathList = state.slides1?.map { it.imagePath ?: "" },
                modifier = Modifier.wrapContentHeight(),
                onSliderClicked = {
                    onSendEvent(HomeContract.Event.OnSliderClicked(0, it))
                })
        }
        item {
            ProductsGroup(
                productsList = state.categoryItems,
                headerTitle = stringResource(id = R.string.home_screen_products_group)
            )
        }
        item {
            CategoryGroup(state.featuredCategories)
        }
        item {
            IndicatorImageSlider(state.slides2?.map { it.imagePath ?: "" },
                modifier = Modifier.wrapContentHeight(),
                onSliderClicked = {
                    onSendEvent(HomeContract.Event.OnSliderClicked(1, it))
                })
        }
        item {

            FeaturedBrands(state.featuredBrands)
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreenContent() {
    HomeScreenContent(state = HomeContract.State(), onSendEvent = {
    })
}