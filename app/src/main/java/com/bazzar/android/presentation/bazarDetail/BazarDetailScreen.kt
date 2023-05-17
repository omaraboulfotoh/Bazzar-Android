package com.bazzar.android.presentation.bazarDetail

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.BazaarModel
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.bazarDetail.composables.BazarDetailScreenContent
import com.bazzar.android.presentation.common.shareText
import com.bazzar.android.presentation.destinations.ProductDetailScreenDestination
import com.bazzar.android.presentation.destinations.ProductScreenDestination
import com.bazzar.android.presentation.homeScreen.HomeContract
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun BazarDetailScreen(
    viewModel: BazarDetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    bazaar: BazaarModel
) {
    // get state
    val state = viewModel.viewState()
    val context = LocalContext.current

    viewModel.sideEffect { effect ->
        when (effect) {
            is BazarDetailContract.Effect.Navigation.GoToProductDetails -> {
                navigator.navigate(
                    ProductDetailScreenDestination(
                        product = effect.product
                    )
                )
            }
            // category, brand
            is BazarDetailContract.Effect.Navigation.GoToBack -> navigator.navigateUp()
            is BazarDetailContract.Effect.OnShareBazaar -> context.shareText(
                effect.shareText,
                effect.shareLink
            )

            is BazarDetailContract.Effect.Navigation.GoToBrandProductsList -> navigator.navigate(
                ProductScreenDestination(brand = effect.brand)
            )

            is BazarDetailContract.Effect.Navigation.GoToCategoryProductsList ->
                navigator.navigate(ProductScreenDestination(category = effect.category))


        }
    }
    // init logic
    viewModel.init(bazaar)

    BazarDetailScreenContent(state = state) { viewModel.setEvent(it) }
}
