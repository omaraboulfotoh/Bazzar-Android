package com.bazzar.android.presentation.wishlist.composables


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.bazzar.android.R
import com.bazzar.android.presentation.bazarListScreen.composables.BazarList
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.wishlist.WishListContract
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

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
                Box(modifier = Modifier.fillMaxSize()) {

                    if (state.showEmptyProducts) {
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
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            state.productCartList?.let {
                                itemsIndexed(it) { index, product ->
                                    ProductWishList(
                                        modifier = Modifier.padding(vertical = BazzarTheme.spacing.xs),
                                        product = product,
                                        onItemClicked = {
                                            onEvent(WishListContract.Event.OnProductClicked(index))
                                        },
                                        onDeleteClicked = {
                                            onEvent(WishListContract.Event.OnDeleteItem(index))
                                        },
                                        onAddToCartClicked = {
                                            onEvent(
                                                WishListContract.Event.OnProductAddToCartClicked(
                                                    index
                                                )
                                            )
                                        })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
