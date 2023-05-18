package com.bazzar.android.presentation.locationScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.UserAddress
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.destinations.AddressScreenDestination
import com.bazzar.android.presentation.locationScreen.composables.LocationScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun LocationScreen(
    viewModel: LocationViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    userAddress: UserAddress? = null,
) {

    // state
    val state = viewModel.viewState()

    viewModel.sideEffect { effect ->
        when (effect) {
            is LocationContract.Effect.Navigation.GoToAddEditAddress ->
                navigator.navigate(AddressScreenDestination(userAddress = effect.userAddress))

            is LocationContract.Effect.Navigation.GoBack ->
                navigator.navigateUp()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.init(userAddress)
    }

    LocationScreenContent(state = state) { viewModel.setEvent(it) }
}