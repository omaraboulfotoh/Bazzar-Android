package com.bazzar.android.presentation.wishlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.R
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.SuccessAddedToCart
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.destinations.BazarDetailScreenDestination
import com.bazzar.android.presentation.destinations.CartScreenDestination
import com.bazzar.android.presentation.destinations.ProductDetailScreenDestination
import com.bazzar.android.presentation.productDetail.ProductDetailContract
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.wishlist.composables.WishListPager
import com.bazzar.android.presentation.wishlist.composables.WishListPagerTabs
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun WishListScreen(
    viewModel: WishListViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {

    val state = viewModel.viewState()
    val pagerState = rememberPagerState()
    val coroutinesScope = rememberCoroutineScope()


    viewModel.sideEffect { effect ->
        when (effect) {
            is WishListContract.Effect.Navigation.GoToBazaar ->
                navigator.navigate(BazarDetailScreenDestination(bazaar = effect.bazaar))

            is WishListContract.Effect.Navigation.GoToProduct ->
                navigator.navigate(ProductDetailScreenDestination(product = effect.product))

            is WishListContract.Effect.ScrollPager -> coroutinesScope.launch {
                pagerState.animateScrollToPage(effect.pager)
            }

            is WishListContract.Effect.Navigation.GoToBack -> navigator.navigateUp()
            WishListContract.Effect.Navigation.GoToCart ->
                navigator.navigate(CartScreenDestination)
        }
    }

    viewModel.init()

    // Setup page change listener
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { pageIndex ->
            viewModel.setEvent(
                WishListContract.Event.OnPageChanged(
                    pageIndex = pageIndex
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = BottomNavigationHeight),
        horizontalAlignment = Alignment.Start
    ) {
        BazzarAppBar(title = stringResource(id = R.string.wish_list), onNavigationClick = {
            viewModel.setEvent(WishListContract.Event.OnBackClicked)
        })
        Box(modifier = Modifier.weight(1f)) {


            // screen layout
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(BazzarTheme.spacing.m),
                verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m),
                horizontalAlignment = Alignment.Start
            ) {
                WishListPagerTabs(
                    tabTitles = listOf(
                        stringResource(id = R.string.products),
                        stringResource(id = R.string.bazzar)
                    ),
                    onTabClicked = {
                        viewModel.setEvent(WishListContract.Event.OnPageChanged(it))
                    },
                    currentPage = state.currentPage,
                    pagerState = PagerState(0),
                )

                WishListPager(state = state, pagerState = pagerState, onEvent = {
                    viewModel.setEvent(it)
                }, modifier = Modifier.weight(1f))
            }

            SuccessAddedToCart(
                modifier = Modifier.align(Alignment.BottomCenter),
                show = state.showSuccessAddedToCart,
                onContinueShoppingClick = { viewModel.setEvent(WishListContract.Event.OnContinueShoppingClicked) },
                onVisitCardClick = { viewModel.setEvent(WishListContract.Event.OnVisitYourCartClicked) },
            )
        }


    }
}
