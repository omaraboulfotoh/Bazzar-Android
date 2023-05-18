package com.bazzar.android.presentation.addAddressScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.UserAddress
import com.bazzar.android.R
import com.bazzar.android.common.navigateAndClearBackStack
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.addAddressScreen.composables.AddressScreenContent
import com.bazzar.android.presentation.destinations.MainScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun AddressScreen(
    viewModel: AddressViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    userAddress: UserAddress = UserAddress(),
) {

    // get state
    val state = viewModel.viewState()
    LaunchedEffect(Unit) {
        // init logic
        viewModel.init(userAddress)
    }
    viewModel.sideEffect { effect ->
        when (effect) {
            is AddressContract.Effect.Navigation.GoToHomeScreen -> {
                navigator.navigateAndClearBackStack(MainScreenDestination())
            }
            is AddressContract.Effect.Navigation.GoToBack -> {
                navigator.navigateUp()
            }
        }
    }
    AddressScreenContent(
        title =
        if (userAddress.areaId == null) stringResource(id = R.string.add_new_address)
        else stringResource(id = R.string.edit_user_address),
        state = state
    ) {
        viewModel.setEvent(it)
    }
}