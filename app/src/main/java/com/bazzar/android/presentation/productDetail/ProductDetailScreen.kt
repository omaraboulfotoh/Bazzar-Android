package com.bazzar.android.presentation.productDetail

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.BazaarModel
import com.android.model.home.Product
import com.bazzar.android.common.buildUrlIntent
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.DeepLinkConstants.PRODUCT_DETAILS_DEEP_LINK
import com.bazzar.android.presentation.SocialMedia
import com.bazzar.android.presentation.common.shareText
import com.bazzar.android.presentation.destinations.CartScreenDestination
import com.bazzar.android.presentation.destinations.ImageViewerScreenDestination
import com.bazzar.android.presentation.destinations.LoginScreenDestination
import com.bazzar.android.presentation.destinations.ProductDetailScreenDestination
import com.bazzar.android.presentation.destinations.ProductScreenDestination
import com.bazzar.android.presentation.productDetail.composables.ProductDetailScreenContent
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(deepLinks = [DeepLink(uriPattern = "$PRODUCT_DETAILS_DEEP_LINK/{itemId}")])
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    product: Product? = null,
    itemId: String? = null,
    bazaar: BazaarModel? = null,
) {

    // context
    val context = LocalContext.current

    // get state
    val state = viewModel.viewState()

    //handle navigation
    viewModel.sideEffect { effect ->
        when (effect) {
            is ProductDetailContract.Effect.Navigation.GoToBack -> navigator.navigateUp()
            is ProductDetailContract.Effect.ShareProduct ->
                context.shareText(effect.shareText, effect.shareLink)

            is ProductDetailContract.Effect.Navigation.GoToCart ->
                navigator.navigate(CartScreenDestination)

            is ProductDetailContract.Effect.Navigation.GoToProductBrandList -> navigator.navigate(
                ProductScreenDestination(brand = effect.brand)
            )

            is ProductDetailContract.Effect.Navigation.GoToImageViewer ->
                navigator.navigate(
                    ImageViewerScreenDestination(
                        imagePathsList = ArrayList(effect.imagePathList),
                        product = effect.product,
                        bazaar = bazaar
                    )
                )

            is ProductDetailContract.Effect.Navigation.GoToOpenProduct ->
                navigator.navigate(ProductDetailScreenDestination(product = effect.product))

            is ProductDetailContract.Effect.Navigation.GoToTalkToUs -> {
                buildUrlIntent(SocialMedia.WHATS_APP).apply {
                    context.startActivity(this)
                }

            }

            ProductDetailContract.Effect.Navigation.GoToLogin ->
                navigator.navigate(LoginScreenDestination())
        }
    }
    // init logic
    viewModel.init(product, itemId, bazaar)
    ProductDetailScreenContent(state = state) { viewModel.setEvent(it) }
}

