package com.bazzar.android.presentation.search.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.homeScreen.composables.ProductsGroup
import com.bazzar.android.presentation.search.SearchScreenContract.Event
import com.bazzar.android.presentation.search.SearchScreenContract.State
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun SearchScreenContent(state: State, onSendEvent: (Event) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BazzarTheme.colors.backgroundColor)
            .padding(bottom = BottomNavigationHeight),
    ) {
        item {
            SearchScreenHeader(
                searchTerm = state.searchTerm ?: "",
                onSearchTermChanged = { onSendEvent(Event.OnSearchTermChanged(it)) },
                onSearchClicked = { onSendEvent(Event.OnSearchClicked) },
                onBackClicked = { onSendEvent(Event.OnBackClicked) }
            )
        }
        item {
            RecentSearchList(
                recentSearchList = state.recentSearchList?.takeLast(6) ?: listOf(),
                onClick = { onSendEvent(Event.OnRecentSearchClicked(it)) },
                onRemoveClick = { index, s ->
                    onSendEvent(
                        Event.OnRemoveRecentSearchClicked(
                            index,
                            s
                        )
                    )
                }
            )
        }
        item {
            if (state.productList.isNullOrEmpty().not()) {
                Spacer(modifier = Modifier.height(BazzarTheme.spacing.xxl))
                ProductsGroup(
                    headerTitle = stringResource(id = R.string.top_rated_products),
                    productsList = state.productList!!,
                    onProductClicked = { onSendEvent(Event.OnProductClicked(it)) },
                    showViewAll = false
                )
            }
        }
    }
}