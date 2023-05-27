package com.bazzar.android.presentation.homeScreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.destinations.BazarDetailScreenDestination
import com.bazzar.android.presentation.destinations.BazarListScreenDestination
import com.bazzar.android.presentation.destinations.CartScreenDestination
import com.bazzar.android.presentation.destinations.LoginScreenDestination
import com.bazzar.android.presentation.destinations.ProductDetailScreenDestination
import com.bazzar.android.presentation.destinations.ProductScreenDestination
import com.bazzar.android.presentation.destinations.SearchScreenDestination
import com.bazzar.android.presentation.homeScreen.composables.HomeScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    navController: NavController,
) {

    // get state
    val state = viewModel.viewState()
    viewModel.sideEffect { effect ->
        when (effect) {
            is HomeContract.Effect.Navigation.GoToProductDetails -> navigator.navigate(
                ProductDetailScreenDestination(effect.product)
            )

            is HomeContract.Effect.Navigation.GoToBrandProductsList ->
                navigator.navigate(ProductScreenDestination(brand = effect.brand))

            is HomeContract.Effect.Navigation.GoToCategoryProductsList ->
                navigator.navigate(ProductScreenDestination(category = effect.category))

            is HomeContract.Effect.Navigation.GoToSearch ->
                navigator.navigate(SearchScreenDestination)

            is HomeContract.Effect.Navigation.GoToBazaarDetails ->
                navigator.navigate(BazarDetailScreenDestination(bazaar = effect.bazaar))

            HomeContract.Effect.Navigation.GoToBazaarsList ->
                navigator.navigate(BazarListScreenDestination)

            is HomeContract.Effect.Navigation.GoToCategoriesScreen -> {

            }

            HomeContract.Effect.Navigation.GoToLogin ->
                navigator.navigate(LoginScreenDestination())

            HomeContract.Effect.Navigation.GoToCart ->
                navigator.navigate(CartScreenDestination)
        }
    }
    // init logic
    viewModel.init()
    HomeScreenContent(state = state) { viewModel.setEvent(it) }
}