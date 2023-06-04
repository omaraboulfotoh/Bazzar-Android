package com.bazzar.android.presentation.productsList

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.Brand
import com.android.model.home.Category
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.startNewTaskMainActivity
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.destinations.LoginScreenDestination
import com.bazzar.android.presentation.DeepLinkConstants.BRAND_PRODUCT_LIST_DEEP_LINK
import com.bazzar.android.presentation.DeepLinkConstants.BRAND_PRODUCT_LIST_HTTP_DEEP_LINK
import com.bazzar.android.presentation.DeepLinkConstants.CATEGORY_PRODUCT_LIST_DEEP_LINK
import com.bazzar.android.presentation.common.shareText
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.destinations.CartScreenDestination
import com.bazzar.android.presentation.destinations.ProductDetailScreenDestination
import com.bazzar.android.presentation.destinations.SearchScreenDestination
import com.bazzar.android.presentation.productsList.composables.ProductScreenContent
import com.bazzar.android.presentation.theme.BazzarTheme
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
    val context = LocalContext.current
    viewModel.sideEffect { effect ->
        when (effect) {
            is ProductContract.Effect.Navigation.GoToProductDetailPage -> {
                navigator.navigate(
                    ProductDetailScreenDestination(
                        product = effect.product
                    )
                )
            }

            is ProductContract.Effect.Navigation.GoToSearch -> navigator.navigate(
                SearchScreenDestination
            )
            // category, brand
            ProductContract.Effect.Navigation.GoToBack -> {
                if (categoryId.isNullOrEmpty() && brandId.isNullOrEmpty()) {
                    navigator.navigateUp()
                } else {
                    context.startNewTaskMainActivity()
                }
            }

            ProductContract.Effect.Navigation.GoToLogin -> navigator.navigate(LoginScreenDestination())

            ProductContract.Effect.Navigation.GoToCart -> navigator.navigate(CartScreenDestination)

            is ProductContract.Effect.Navigation.ShareBrand -> context.shareText(
                effect.shareText, effect.shareLink
            )
        }
    }

    BackHandler {
        if (categoryId.isNullOrEmpty().not() || brandId.isNullOrEmpty().not()) {
            context.startNewTaskMainActivity()
        } else {
            navigator.navigateUp()
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

    ProductScreenContent(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor)
            .padding(
                bottom = if (categoryId.isNullOrEmpty() && brandId.isNullOrEmpty()) BottomNavigationHeight
                else 0.dp
            ), state = state
    ) {
        viewModel.setEvent(it)
    }
}
