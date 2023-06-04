package com.bazzar.android.presentation.productDetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.BazaarModel
import com.android.model.home.Product
import com.bazzar.android.common.buildUrlIntent
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.startNewTaskMainActivity
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.DeepLinkConstants.PRODUCT_DETAILS_DEEP_LINK
import com.bazzar.android.presentation.DeepLinkConstants.PRODUCT_DETAILS_HTTP_DEEP_LINK
import com.bazzar.android.presentation.SocialMedia
import com.bazzar.android.presentation.common.shareText
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.destinations.CartScreenDestination
import com.bazzar.android.presentation.destinations.ImageViewerScreenDestination
import com.bazzar.android.presentation.destinations.LoginScreenDestination
import com.bazzar.android.presentation.destinations.ProductDetailScreenDestination
import com.bazzar.android.presentation.destinations.ProductScreenDestination
import com.bazzar.android.presentation.productDetail.composables.ProductDetailScreenContent
import com.bazzar.android.presentation.theme.BazzarTheme
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(
    deepLinks = [
        DeepLink(uriPattern = "$PRODUCT_DETAILS_DEEP_LINK/{itemId}"),
        DeepLink(uriPattern = "$PRODUCT_DETAILS_HTTP_DEEP_LINK/{itemId}"),
    ]
)
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
            is ProductDetailContract.Effect.Navigation.GoToBack -> {
                if (itemId.isNullOrEmpty()) navigator.navigateUp()
                else context.startNewTaskMainActivity()
            }
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

            is ProductDetailContract.Effect.Navigation.GoToTalkToUs ->
                buildUrlIntent(SocialMedia.WHATS_APP + effect.whatsAppNumber).apply {
                    context.startActivity(this)
                }

            ProductDetailContract.Effect.Navigation.GoToLogin ->
                navigator.navigate(LoginScreenDestination())
        }
    }

    BackHandler {
        if (itemId.isNullOrEmpty()) navigator.navigateUp()
        else context.startNewTaskMainActivity()
    }

    // init logic
    viewModel.init(product, itemId, bazaar)
    ProductDetailScreenContent(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor)
            .padding(bottom = if (itemId.isNullOrEmpty()) BottomNavigationHeight else 0.dp),
        state = state
    ) {
        viewModel.setEvent(it)
    }
}

