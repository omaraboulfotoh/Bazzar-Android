package com.bazzar.android.presentation.product_screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.Product
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.Constants.BRAND_KEY
import com.bazzar.android.presentation.Constants.SUB_CATEGORY_ID_KEY
import com.bazzar.android.presentation.product_screen.composables.ProductScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    // receive data from previous Screen

    //send data to viewModel
  /*  viewModel.savedStateHandle.set(SUB_CATEGORY_ID_KEY, subCategory)
    viewModel.savedStateHandle.set(SUB_CATEGORY_ID_KEY, subCategoryId)
    viewModel.savedStateHandle.set(BRAND_KEY, brand)*/
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
    viewModel.init()
//    viewModel.categoryId=ProductScreenDestination.arguments.get()

    ProductScreenContent(state = state) { viewModel.setEvent(it) }
}

private fun goToProductDetailPage(product: Product, navigator: DestinationsNavigator) {
//    navigator.navigate()
}
