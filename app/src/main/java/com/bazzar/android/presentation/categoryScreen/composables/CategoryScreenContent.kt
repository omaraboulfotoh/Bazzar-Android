package com.bazzar.android.presentation.categoryScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bazzar.android.presentation.categoryScreen.CategoryContract
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun CategoryScreenContent(
    state: CategoryContract.State,
    onSendEvent: (CategoryContract.Event) -> Unit,
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor)
            .padding(bottom = BottomNavigationHeight)
            .padding(horizontal = BazzarTheme.spacing.m),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
    ) {
        item {
            BrandCategoryHeader(
                state.showCategories.not(),
                onSearchClick = { onSendEvent(CategoryContract.Event.OnSearchClicked) }
            )
        }
        item {
            ToggleBrandCategory(
                onToggle = { onSendEvent(CategoryContract.Event.OnToggleClicked) },
                isCategory = state.showCategories
            )
        }
        if (state.showCategories) {
            CategoryList(
                state.mainCategorisesList.orEmpty(),
                state.subCategoriesList.orEmpty(),
                onCategoryItemClicked = {
                    onSendEvent(CategoryContract.Event.OnCategoryItemClicked(it))
                },
                onSubCategoryItemClicked = {
                    onSendEvent(
                        CategoryContract.Event.OnSubCategoryItemClicked(it)
                    )
                }, onDismissClicked = {
                    onSendEvent(
                        CategoryContract.Event.OnDismissClicked
                    )
                })
        } else {
            BrandGrid(
                brandList = state.brandListToShow.orEmpty(),
                searchBrandTerm = state.searchBrandTerm,
                isSearchBrandOpen = state.isSearchBrandOpen,
                onSearchTermChanged = { onSendEvent(CategoryContract.Event.OnSearchBrandChanged(it)) },
                onSearchClick = { onSendEvent(CategoryContract.Event.OnSearchBrandClicked) },
                onCancelSearchClicked = { onSendEvent(CategoryContract.Event.OnCancelSearchBrandClicked) },
                onBrandClicked = { onSendEvent(CategoryContract.Event.OnBrandItemClicked(it)) }
            )
        }
        item {
            Spacer(modifier = Modifier.height(BazzarTheme.spacing.s))
        }
    }
}

@Preview
@Composable
fun CategoryScreenContentPreview() {
    CategoryScreenContent(state = CategoryContract.State(), onSendEvent = {})
}