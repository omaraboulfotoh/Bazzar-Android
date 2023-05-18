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
import com.bazzar.android.presentation.composables.ErrorView
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.homeScreen.HomeContract
import com.bazzar.android.presentation.homeScreen.HomeContract.*
import com.bazzar.android.presentation.theme.BazzarTheme


@Composable
fun HomeScreenContent(state: State, onSendEvent: (Event) -> Unit) {
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
                HomeHeader(onSearchClicked = { onSendEvent(Event.OnSearchClicked) })
            }
            item {
                IndicatorHomeImageSlider(imagePathList = state.slides1.orEmpty()
                    .mapNotNull { it.imagePath ?: "" },
                    modifier = Modifier.wrapContentHeight(),
                    onSliderClicked = {
                        onSendEvent(Event.OnSliderClicked(0, it))
                    })
            }
            if (state.featuredBazzars.isNullOrEmpty().not())
                item {
                    FeaturedBazaarSlider(slides2 = state.featuredBazzars.orEmpty(),
                        onShowAllClicked = {
                            onSendEvent(Event.OnShowAllBazaars)
                        }, onBazaarClicked = {
                            onSendEvent(Event.OnBazaarClicked(it))
                        })
                }
            if (state.featuredCategories.isNullOrEmpty().not())
                CategoryGroup(state.featuredCategories.orEmpty(), onCategoryClicked = {
                    onSendEvent(Event.OnCategoryClicked(it))
                }, onShowAllClicked = {
                    onSendEvent(Event.OnShowAllCategories)
                })

            state.categoryItems.orEmpty().forEachIndexed { index, productSection ->
                item {
                    ProductsGroup(productsList = productSection.items.orEmpty(),
                        headerTitle = productSection.title.orEmpty(),
                        onProductClicked = { itemId ->
                            onSendEvent(Event.OnProductClicked(itemId, index))
                        },
                        onProductFavClicked = { itemId ->
                            onSendEvent(Event.OnProductFavClicked(itemId, index))
                        },
                        onShowAllClicked = {
                            onSendEvent(Event.OnShowAllProducts(index))
                        })
                    Spacer(modifier = Modifier.height(BazzarTheme.spacing.s))
                }
            }

            item {
                IndicatorHomeImageSlider(state.slides2.orEmpty().mapNotNull { it.imagePath ?: "" },
                    modifier = Modifier.wrapContentHeight(),
                    onSliderClicked = {
                        onSendEvent(Event.OnSliderClicked(1, it))
                    })
            }
            if (state.featuredBrands.isNullOrEmpty().not())
                FeaturedBrands(state.featuredBrands.orEmpty(), onBrandClicked = {
                    onSendEvent(Event.OnBrandClicked(it))
                }, onShowAllClicked = {
                    onSendEvent(Event.OnShowAllBrands)
                })

            item {
                Spacer(modifier = Modifier.height(BazzarTheme.spacing.xs))
            }
        }

        if (state.showError)
            ErrorView(modifier = Modifier.fillMaxSize(), onTryAgainClicked = {
                onSendEvent(Event.OnTryAgainClicked)
            })
//        if (state.adShown.not())
        if (1 != 1)
            AdDialog(
                imagePath = state.ads?.firstOrNull()?.imagePath,
                onDismiss = { onSendEvent(Event.OnAdDismissed) },
                onPositive = {
                    onSendEvent(Event.OnAdClicked)
                }
            )
    }
}

@Preview
@Composable
fun PreviewHomeScreenContent() {
    HomeScreenContent(state = State(), onSendEvent = {})
}