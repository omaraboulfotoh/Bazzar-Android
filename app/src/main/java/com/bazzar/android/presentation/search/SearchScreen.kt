package com.bazzar.android.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.destinations.LoginScreenDestination
import com.bazzar.android.presentation.destinations.ProductDetailScreenDestination
import com.bazzar.android.presentation.destinations.ProductScreenDestination
import com.bazzar.android.presentation.search.composables.SearchScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    // get state
    val state = viewModel.viewState()

    viewModel.sideEffect { effect ->
        when (effect) {
            is SearchScreenContract.Effect.Navigation.GoBack -> navigator.navigateUp()
            is SearchScreenContract.Effect.Navigation.GoToProductScreen ->
                navigator.navigate(ProductScreenDestination(searchTerm = effect.searchTerm))

            is SearchScreenContract.Effect.Navigation.GoToProductDetails ->
                navigator.navigate(ProductDetailScreenDestination(product = effect.product))

            SearchScreenContract.Effect.Navigation.GoToLogin ->
                navigator.navigate(LoginScreenDestination())
        }
    }

    LaunchedEffect(Unit) {
        viewModel.init()
    }

    SearchScreenContent(state = state) { viewModel.setEvent(it) }
}