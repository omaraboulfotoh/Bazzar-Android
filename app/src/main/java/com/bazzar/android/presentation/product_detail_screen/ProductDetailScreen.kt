package com.bazzar.android.presentation.product_screen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.Product
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.Constants
import com.bazzar.android.presentation.product_screen.composables.ProductDetailScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    // receive data from previous Screen
    val args = navBackStackEntry.arguments

    //send data to viewModel
    viewModel.savedStateHandle.set(Constants.PRODUCT_KEY, product)

    // get state
    val state = viewModel.viewState()

    //handle navigation
    viewModel.sideEffect { effect ->
        when (effect) {
            is ProductDetailContract.Effect.Navigation.back -> {}
            is ProductDetailContract.Effect.Navigation.Share -> TODO()
            is ProductDetailContract.Effect.Navigation.goToCart -> TODO()
            else -> {}
        }
    }
    // init logic
    viewModel.init()
//    viewModel.categoryId=ProductScreenDestination.arguments.get()

    ProductDetailScreenContent(state = state) { viewModel.setEvent(it) }
}

