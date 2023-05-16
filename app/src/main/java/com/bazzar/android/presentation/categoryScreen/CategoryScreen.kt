package com.bazzar.android.presentation.categoryScreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.categoryScreen.composables.CategoryScreenContent
import com.bazzar.android.presentation.destinations.CategoryScreenDestination
import com.bazzar.android.presentation.destinations.ProductScreenDestination
import com.bazzar.android.presentation.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun CategoryScreen(
    viewModel: CategoriesViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    showCategories: Boolean = true
) {

    // get state
    val state = viewModel.viewState()
    viewModel.sideEffect { effect ->
        when (effect) {
            is CategoryContract.Effect.Navigation.GoToProductCategoryList -> {
                navigator.navigate(ProductScreenDestination(category = effect.category))

            }

            is CategoryContract.Effect.Navigation.GoToProductBrandList -> {
                navigator.navigate(ProductScreenDestination(brand = effect.brand))
            }

            is CategoryContract.Effect.Navigation.GoToSearch ->
                navigator.navigate(SearchScreenDestination)
        }
    }
    // init logic
    viewModel.init(showCategories)
    CategoryScreenContent(state = state) { viewModel.setEvent(it) }
}