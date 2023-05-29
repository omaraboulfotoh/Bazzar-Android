package com.bazzar.android.presentation.changePassword

import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.R
import com.bazzar.android.common.isValidPassword
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.changePassword.ChangePasswordContract.Effect
import com.bazzar.android.presentation.changePassword.ChangePasswordContract.Event
import com.bazzar.android.presentation.changePassword.ChangePasswordContract.State
import com.bazzar.android.utils.IResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
) : BaseViewModel<Event, State, Effect>(globalState) {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.OnNewPasswordChanged -> setState { copy(newPassword = event.newPass) }
            is Event.OnCurrentPasswordChanged -> setState { copy(currentPassword = event.currentPass) }
            is Event.OnChangePasswordClicked -> changePassword()
            is Event.OnGoBack -> setEffect {
                Effect.Navigation.OnGoBack
            }
        }
    }

    private fun changePassword() = executeCatching({
        if (currentState.currentPassword.isNullOrEmpty() || currentState.newPassword.isNullOrEmpty()) {
            globalState.error(R.string.current_and_new_password_required)
            return@executeCatching
        } else if (currentState.newPassword?.isValidPassword() != true) {
            globalState.error(R.string.password_error_validation)
            return@executeCatching
        }

        homeUseCase.changePassword(
            currentPassword = currentState.currentPassword.orEmpty(),
            newPassword = currentState.newPassword.orEmpty()
        ).collect { changePasswordResponse ->
            when (changePasswordResponse) {
                is Result.Error -> globalState.error(changePasswordResponse.message.orEmpty())
                is Result.Loading -> globalState.loading(true)
                is Result.Success -> {
                    if (changePasswordResponse.data == true) {
                        setEffect { Effect.Navigation.GoToMainScreen }
                    } else {
                        globalState.error(changePasswordResponse.message.orEmpty())
                    }
                }
            }
        }
    })

}