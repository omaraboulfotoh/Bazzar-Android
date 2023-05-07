package com.bazzar.android.presentation.bazarListScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.Category
import com.android.model.home.toBrand
import com.android.model.home.toCategory
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.bazarListScreen.composables.BazarListScreenContent
import com.bazzar.android.presentation.destinations.BazarDetailScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun BazarListScreen(
    viewModel: BazarListViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val state = viewModel.viewState()

    LaunchedEffect(Unit) {
        viewModel.getAllBazars()
    }

    viewModel.sideEffect { effect ->
        when (effect) {
            is BazarListContract.Effect.Navigation.GoToBazarDetailsScreen -> {
                navigator.navigate(BazarDetailScreenDestination(
                    category = effect.bazar.toCategory(),
                    brand = effect.bazar.toBrand().copy(id = null, title = null)
                ))
            }
        }
    }

    BazarListScreenContent(state) { viewModel.setEvent(it) }
}