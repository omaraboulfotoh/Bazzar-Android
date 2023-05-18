package com.bazzar.android.presentation.wishlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.R
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.bazarListScreen.BazarListContract
import com.bazzar.android.presentation.bazarListScreen.composables.BazarList
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.wishlist.composables.WishListPagerTabs
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalPagerApi::class)
@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun WishListScreen(
    viewModel: WishListViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {

    val state = viewModel.viewState()
    val pagerState = rememberPagerState()

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
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start
    ) {
        BazzarAppBar(title = stringResource(id = R.string.wish_list), onNavigationClick = {
            viewModel.setEvent(WishListContract.Event.OnBackClicked)
        })

        // screen layout
        Column(
            modifier = Modifier
                .weight(1f)
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
                pagerState = PagerState(0),
            )

            WishListPager(state = state, pagerState = pagerState, onEvent = {
                viewModel.setEvent(it)
            }, modifier = Modifier.weight(1f))
        }

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WishListPager(
    state: WishListContract.State,
    pagerState: PagerState,
    onEvent: (WishListContract.Event) -> Unit,
    modifier: Modifier = Modifier
) {
    // Pager
    HorizontalPager(
        count = 2,
        state = pagerState,
        key = { it },
        modifier = modifier
    ) { pageIndex ->
        // Get vars dependant on current page
        when (pageIndex) {
            1 -> {
                if (state.showEmptyBazaars) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.Center),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_empty_view),
                            contentDescription = "ic_empty_view"
                        )
                    }
                } else {
                    BazarList(
                        modifier = Modifier
                            .fillMaxSize(),
                        bazarList = state.bazaarList.orEmpty(),
                        onBazarItemFavClick = {
                            onEvent(WishListContract.Event.OnDeleteBazaar(it))
                        }, onBazarItemClick = {
                            onEvent(WishListContract.Event.OnBazaarClicked(it))
                        }
                    )
                }
            }
            else -> {

            }
        }
    }
}

