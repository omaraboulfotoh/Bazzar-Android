package com.bazzar.android.presentation.category_screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.category_screen.composables.CategoryScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun CategoryScreen(
    viewModel: CategoriesViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {

    // get state
    val state = viewModel.viewState()
    viewModel.sideEffect { effect ->
        when (effect) {
            is CategoryContract.Effect.Navigation.GoToProductCategoryList -> {}
            is CategoryContract.Effect.Navigation.GoToProductBrandList -> {}
        }
    }
    // init logic
    viewModel.init()
    CategoryScreenContent(state = state) { viewModel.setEvent(it) }
}