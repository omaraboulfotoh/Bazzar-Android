package com.bazzar.android.presentation.register

import com.android.model.home.UserData
import com.android.model.request.UserRegisterRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.R
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.utils.IResourceProvider
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
            is RegisterContract.Event.OnEmailChanged -> setState { copy(email = event.email) }
            is RegisterContract.Event.OnNameChanged -> setState { copy(fullName = event.name) }
            is RegisterContract.Event.OnPhoneChanged -> setState { copy(phoneNumber = event.phone) }
            RegisterContract.Event.OnBackClicked -> setEffect { RegisterContract.Effect.Navigation.GoBack }
        }
    }

    private fun handleRegisterClicked() {
        if (currentState.isAgreeTermsAndConditions.not()) {
            globalState.error(resourceProvider.getString(R.string.terms_and_condition_required))
        } else {
            // todo should handle the validation for each field and take care that phone number max digits is 7 without +965
            val userRegisterRequest = UserRegisterRequest(
                name = currentState.fullName.orEmpty(),
                englishName = currentState.fullName.orEmpty(),
                email = currentState.email.orEmpty(),
                phone = "+965${currentState.phoneNumber.orEmpty()}"
            )
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
