package com.bazzar.android.presentation.editProfile

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.model.home.UserData
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.destinations.ChangePasswordScreenDestination
import com.bazzar.android.presentation.destinations.EditProfileScreenDestination
import com.bazzar.android.presentation.editProfile.composables.EditProfileScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    userData: UserData,
) {
    // get state
    val state = viewModel.viewState()
    viewModel.sideEffect { effect ->
        when (effect) {
            EditProfileContract.Effect.Navigation.GoBack -> navigator.navigateUp()
            EditProfileContract.Effect.Navigation.GoToChangePassword ->
                navigator.navigate(ChangePasswordScreenDestination)

        }
    }
    // init logic
    viewModel.init(userData)

    EditProfileScreenContent(state = state) { viewModel.setEvent(it) }
}
