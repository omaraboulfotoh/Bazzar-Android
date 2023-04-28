package com.bazzar.android.presentation.productDetail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.Product
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.destinations.ProductDetailScreenDestination
import com.bazzar.android.presentation.destinations.ProductScreenDestination
import com.bazzar.android.presentation.productDetail.composables.ProductDetailScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    product: Product
) {

    // get state
    val state = viewModel.viewState()

    //handle navigation
    viewModel.sideEffect { effect ->
        when (effect) {
            is ProductDetailContract.Effect.Navigation.GoToBack -> navigator.navigateUp()
            is ProductDetailContract.Effect.ShareProduct -> {}
            is ProductDetailContract.Effect.Navigation.GoToCart -> {}
            is ProductDetailContract.Effect.Navigation.GoToProductBrandList -> navigator.navigate(
                ProductScreenDestination(brand = effect.brand)
            )

            is ProductDetailContract.Effect.Navigation.GoToOpenProduct ->
                navigator.navigate(ProductDetailScreenDestination(product = effect.product))
        }
    }
    // init logic
    viewModel.init(product)
    ProductDetailScreenContent(state = state) { viewModel.setEvent(it) }
}

