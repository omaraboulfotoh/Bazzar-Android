package com.bazzar.android.presentation.accountScreen

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.accountScreen.composables.AccountScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {


    val state = viewModel.viewState()

    viewModel.sideEffect { effect ->
        when (effect) {
            else -> {}
        }
    }
    AccountScreenContent()
}