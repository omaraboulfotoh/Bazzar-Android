package com.bazzar.android.presentation.register_screen

import com.android.model.home.UserData
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
) :
    BaseViewModel<RegisterContract.Event, RegisterContract.State, RegisterContract.Effect>(
        globalState
    ) {

    private var isInitialized = false
    override fun setInitialState() = RegisterContract.State()

    override fun handleEvents(event: RegisterContract.Event) {
        when (event) {
            is RegisterContract.Event.OnAgreeTermsAndConditions -> setState {
                copy(
                    isAgreeTermsAndConditions = isAgreeTermsAndConditions.not()
                )
            }
            is RegisterContract.Event.OnCreateAccount -> {
                if (currentState.isAgreeTermsAndConditions) {
                    register(currentState.userData)
                }
            }
        }
    }

    private fun navigateToOtpScreen() {
        // navigate to otpScreen
        if (currentState.userData.id!! > 0 && currentState.isAgreeTermsAndConditions)
            setEffect { RegisterContract.Effect.Navigation.GoToOtpScreen(currentState.userData) }
    }


    fun init() {
        if (isInitialized.not()) {
            isInitialized = true
        }
    }

    private fun register(userData: UserData) = executeCatching({
        homeUseCase.register(userData)
            .collect { registerResponse ->
                when (registerResponse) {
                    is Result.Error -> globalState.error(registerResponse.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> {
                        setState {
                            copy(
                                userData.id = registerResponse.data
                            )
                        }
                        navigateToOtpScreen()
                    }
                    else -> {}
                }
            }
    })
}
