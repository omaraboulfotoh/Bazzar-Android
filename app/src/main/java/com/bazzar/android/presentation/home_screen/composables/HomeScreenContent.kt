package com.bazzar.android.presentation.home_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.home_screen.HomeContract
import com.bazzar.android.presentation.theme.BazzarTheme


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
            BazzarSlider(state.slides1, onSliderClicked = {
                onSendEvent(HomeContract.Event.OnSliderClicked(0, it))
            })
        }
/*
        item {
            FeaturedBazzarSlider(state.slides2)
        }
*/
        item {
            ProductsGroup(state.categoryItems)
        }
        item {
            CategoryGroup(state.featuredCategories)
        }
        item {
            BazzarSlider(state.slides2, onSliderClicked = {
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