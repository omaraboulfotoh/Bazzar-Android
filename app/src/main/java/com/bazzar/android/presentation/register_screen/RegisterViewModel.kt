package com.bazzar.android.presentation.register_screen

import com.android.model.home.UserData
import com.android.model.request.UserRegisterRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.R
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.utils.IResourceProvider
import com.bazzar.android.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val resourceProvider: IResourceProvider
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

            is RegisterContract.Event.OnCreateAccount -> handleRegisterClicked()
        }
    }

    private fun handleRegisterClicked() {
        if (currentState.isAgreeTermsAndConditions.not()) {
            globalState.error(resourceProvider.getString(R.string.terms_and_condition_required))
        } else {
            val userRegisterRequest = currentState.request ?: return
            register(userRegisterRequest)
        }
    }

    private fun navigateToOtpScreen(data: UserData?) {
        // navigate to otpScreen
        data?.let {
            setEffect { RegisterContract.Effect.Navigation.GoToOtpScreen(it) }
        }
    }


    fun init() {
        if (isInitialized.not()) {
            isInitialized = true
        }
    }

    private fun register(request: UserRegisterRequest) = executeCatching({
        homeUseCase.register(request)
            .collect { registerResponse ->
                when (registerResponse) {
                    is Result.Error -> globalState.error(registerResponse.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> {
                        navigateToOtpScreen(registerResponse.data)
                    }

                    else -> {}
                }
            }
    })
}
