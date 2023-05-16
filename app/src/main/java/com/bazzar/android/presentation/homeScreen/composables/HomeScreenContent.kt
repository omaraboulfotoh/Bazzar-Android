package com.bazzar.android.presentation.homeScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.AdDialog
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.homeScreen.HomeContract
import com.bazzar.android.presentation.theme.BazzarTheme


@Composable
fun HomeScreenContent(state: HomeContract.State, onSendEvent: (HomeContract.Event) -> Unit) {
    Box(
        modifier = Modifier
            .background(BazzarTheme.colors.backgroundColor)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.padding(bottom = BottomNavigationHeight),
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m),
            horizontalAlignment = Alignment.Start,
        ) {
            item {
                HomeHeader(onSearchClicked = { onSendEvent(HomeContract.Event.OnSearchClicked) })
            }
            item {
                IndicatorHomeImageSlider(imagePathList = state.slides1.orEmpty()
                    .mapNotNull { it.imagePath ?: "" },
                    modifier = Modifier.wrapContentHeight(),
                    onSliderClicked = {
                        onSendEvent(HomeContract.Event.OnSliderClicked(0, it))
                    })
            }
            if (state.featuredBazzars.isNullOrEmpty().not())
                item {
                    FeaturedBazaarSlider(slides2 = state.featuredBazzars.orEmpty(),
                        onShowAllClicked = {
                            onSendEvent(HomeContract.Event.OnShowAllBazaars)
                        })
                }
            if (state.featuredCategories.isNullOrEmpty().not())
                CategoryGroup(state.featuredCategories.orEmpty(), onCategoryClicked = {
                    onSendEvent(HomeContract.Event.OnCategoryClicked(it))
                }, onShowAllClicked = {
                    onSendEvent(HomeContract.Event.OnShowAllCategories)
                })

            state.categoryItems.orEmpty().forEachIndexed { index, productSection ->
                item {
                    ProductsGroup(productsList = productSection.items.orEmpty(),
                        headerTitle = stringResource(id = R.string.home_screen_products_group),
                        onProductClicked = { itemId ->
                            onSendEvent(HomeContract.Event.OnProductClicked(itemId, index))
                        },
                        onShowAllClicked = {
                            onSendEvent(HomeContract.Event.OnShowAllProducts(index))
                        })
                }
            }

            item {
                IndicatorHomeImageSlider(state.slides2.orEmpty().mapNotNull { it.imagePath ?: "" },
                    modifier = Modifier.wrapContentHeight(),
                    onSliderClicked = {
                        onSendEvent(HomeContract.Event.OnSliderClicked(1, it))
                    })
            }
            if (state.featuredBrands.isNullOrEmpty().not())
                FeaturedBrands(state.featuredBrands.orEmpty(), onBrandClicked = {
                    onSendEvent(HomeContract.Event.OnBrandClicked(it))
                }, onShowAllClicked = {
                    onSendEvent(HomeContract.Event.OnShowAllBrands)
                })

            item {
                Spacer(modifier = Modifier.height(BazzarTheme.spacing.xs))
            }
        }

        AdDialog(
            imagePath = state.ads?.firstOrNull()?.imagePath,
            onDismiss = { },
            onPositive = {
                onSendEvent(HomeContract.Event.OnAdClicked)
            }
        )
    }
}

@Preview
@Composable
fun PreviewHomeScreenContent() {
    HomeScreenContent(state = HomeContract.State(), onSendEvent = {})
}