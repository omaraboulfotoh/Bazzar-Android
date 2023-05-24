package com.bazzar.android.presentation.imageViewer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.BazaarModel
import com.android.model.home.Product
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.destinations.CartScreenDestination
import com.bazzar.android.presentation.destinations.LoginScreenDestination
import com.bazzar.android.presentation.imageViewer.composables.ImageViewerScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun ImageViewerScreen(
    viewModel: ImageViewerViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    imagePathsList: ArrayList<String>,
    product: Product? = null,
    bazaar: BazaarModel? = null
) {
    // state
    val state = viewModel.viewState()

    viewModel.sideEffect { effect ->
        when (effect) {
            is ImageViewerContract.Effect.Navigation.GoToCart ->
                navigator.navigate(CartScreenDestination)

            is ImageViewerContract.Effect.Navigation.GoBack -> navigator.navigateUp()
            ImageViewerContract.Effect.Navigation.GoToLogin ->
                navigator.navigate(LoginScreenDestination())
        }
    }

    LaunchedEffect(Unit) {
        viewModel.init(imagePathsList, product, bazaar)
    }

    ImageViewerScreenContent(state = state) { viewModel.setEvent(it) }
}