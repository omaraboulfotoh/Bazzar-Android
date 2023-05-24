package com.bazzar.android.presentation.productsList

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.Brand
import com.android.model.home.Category
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.destinations.LoginScreenDestination
import com.bazzar.android.presentation.DeepLinkConstants.BRAND_PRODUCT_LIST_DEEP_LINK
import com.bazzar.android.presentation.DeepLinkConstants.BRAND_PRODUCT_LIST_HTTP_DEEP_LINK
import com.bazzar.android.presentation.DeepLinkConstants.CATEGORY_PRODUCT_LIST_DEEP_LINK
import com.bazzar.android.presentation.destinations.CartScreenDestination
import com.bazzar.android.presentation.destinations.ProductDetailScreenDestination
import com.bazzar.android.presentation.destinations.SearchScreenDestination
import com.bazzar.android.presentation.productsList.composables.ProductScreenContent
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(
    deepLinks = [
        DeepLink(uriPattern = "$BRAND_PRODUCT_LIST_DEEP_LINK/{brandId}"),
        DeepLink(uriPattern = "$CATEGORY_PRODUCT_LIST_DEEP_LINK/{categoryId}"),
        DeepLink(uriPattern = "$BRAND_PRODUCT_LIST_HTTP_DEEP_LINK/{brandId}"),
    ]
)
fun ProductScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    brand: Brand? = null,
    category: Category? = null,
    searchTerm: String? = null,
    brandId: String? = null,
    categoryId: String? = null
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
                navigator.navigate(LoginScreenDestination())

            ProductContract.Effect.Navigation.GoToCart ->
                navigator.navigate(CartScreenDestination)
        }
    }
    // init logic
    viewModel.init(
        brand = brand,
        category = category,
        searchTerm = searchTerm,
        brandId = brandId,
        categoryId = categoryId,
    )

    ProductScreenContent(state = state) { viewModel.setEvent(it) }
}
