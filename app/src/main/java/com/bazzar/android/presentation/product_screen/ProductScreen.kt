package com.bazzar.android.presentation.product_screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.Product
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.product_screen.composables.ProductScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    brand: Brand? = null,
    category: Category? = null,
) {
    // get state
    val state = viewModel.viewState()
    viewModel.sideEffect { effect ->
        when (effect) {
            is ProductContract.Effect.Navigation.GoToProductDetailPage -> {
                goToProductDetailPage(effect.product, navigator)
            }
            // category, brand
            ProductContract.Effect.Navigation.back -> TODO()
        }
    }
    // init logic
    viewModel.init(brand, category)

    ProductScreenContent(state = state) { viewModel.setEvent(it) }
}

private fun goToProductDetailPage(product: Product, navigator: DestinationsNavigator) {
//    navigator.navigate()
}
