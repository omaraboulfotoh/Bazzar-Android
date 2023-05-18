package com.bazzar.android.presentation.productsList

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.Brand
import com.android.model.home.Category
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.destinations.LoginScreenDestination
import com.bazzar.android.presentation.destinations.ProductDetailScreenDestination
import com.bazzar.android.presentation.destinations.SearchScreenDestination
import com.bazzar.android.presentation.productsList.composables.ProductScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    brand: Brand? = null,
    category: Category? = null,
    searchTerm: String? = null
) {
    // get state
    val state = viewModel.viewState()
    viewModel.sideEffect { effect ->
        when (effect) {
            is ProductContract.Effect.Navigation.GoToProductDetailPage -> {
                navigator.navigate(
                    ProductDetailScreenDestination(
                        product = effect.product
                    )
                )
            }

            is ProductContract.Effect.Navigation.GoToSearch ->
                navigator.navigate(SearchScreenDestination)
            // category, brand
            ProductContract.Effect.Navigation.GoToBack -> navigator.navigateUp()
            ProductContract.Effect.Navigation.GoToLogin ->
                navigator.navigate(LoginScreenDestination)
        }
    }
    // init logic
    viewModel.init(brand, category, searchTerm)

    ProductScreenContent(state = state) { viewModel.setEvent(it) }
}
