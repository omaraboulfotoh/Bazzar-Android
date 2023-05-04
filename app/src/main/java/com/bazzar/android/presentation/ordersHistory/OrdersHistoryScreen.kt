package com.bazzar.android.presentation.ordersHistory

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.ordersHistory.composables.OrdersHistoryScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun OrdersHistoryScreen(
    viewModel: OrdersHistoryViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val state = viewModel.viewState()

    viewModel.sideEffect { effect ->
        when (effect) {
            else -> {}
        }
    }

    OrdersHistoryScreenContent()
}