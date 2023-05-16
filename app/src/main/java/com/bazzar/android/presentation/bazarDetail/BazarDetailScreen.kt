package com.bazzar.android.presentation.bazarDetail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.BazaarModel
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.bazarDetail.composables.BazarDetailScreenContent
import com.bazzar.android.presentation.destinations.ProductDetailScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun BazarDetailScreen(
    viewModel: BazarDetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    bazaar : BazaarModel
) {
    // get state
    val state = viewModel.viewState()
    viewModel.sideEffect { effect ->
        when (effect) {
            is BazarDetailContract.Effect.Navigation.GoToProductDetailPage -> {
                navigator.navigate(
                    ProductDetailScreenDestination(
                        product = effect.product
                    )
                )
            }
            // category, brand
            is BazarDetailContract.Effect.Navigation.GoToBack -> navigator.navigateUp()
        }
    }
    // init logic
    viewModel.init(bazaar)

    BazarDetailScreenContent(state = state) { viewModel.setEvent(it) }
}
