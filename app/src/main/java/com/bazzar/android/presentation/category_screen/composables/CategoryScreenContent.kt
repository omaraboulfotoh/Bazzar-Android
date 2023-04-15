package com.bazzar.android.presentation.category_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.bazzar.android.R
import com.bazzar.android.presentation.category_screen.CategoryContract
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun CategoryScreenContent(
    state: CategoryContract.State,
    onSendEvent: (CategoryContract.Event) -> Unit,
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white_smoke)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            BrandCategoryHeader(state.showCategories.not())
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
                state.mainCategorisesList.orEmpty(),
                onCategoryItemClicked = {
                    onSendEvent(CategoryContract.Event.OnCategoryItemClicked(it))
                },
                onSubCategoryItemClicked = {
                    onSendEvent(
                        CategoryContract.Event.OnSubCategoryItemClicked(it)
                    )
                })
        } else {
            item {
                BrandGrid(
                    modifier = Modifier.padding(all = BazzarTheme.spacing.m),
                    brandList = state.brandList.orEmpty(),
                    onBrandClicked = { onSendEvent(CategoryContract.Event.OnBrandItemClicked(it)) }
                )
            }
        }
    }
}

@Preview
@Composable
fun CategoryScreenContentPreview() {
    CategoryScreenContent(state = CategoryContract.State(), onSendEvent = {})
}