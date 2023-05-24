package com.bazzar.android.presentation.register

import android.content.Context
import android.util.Patterns
import com.android.local.SharedPrefersManager
import com.android.model.home.UserData
import com.android.model.request.UserRegisterRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.BazzarApplication
import com.bazzar.android.R
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.utils.IResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val resourceProvider: IResourceProvider,
    private val sharedPrefersManager: SharedPrefersManager
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
            val email = currentState.email.orEmpty()
            val name = currentState.fullName.orEmpty()
            val phone = currentState.phoneNumber.orEmpty()
            if (isValid(email, name, phone)) {
                val userRegisterRequest = UserRegisterRequest(
                    id = sharedPrefersManager.getUserData()?.id,
                    name = name,
                    englishName = name,
                    phone = "+965${phone}"
                )
                register(userRegisterRequest)
            }
        }
    }

    private fun isValid(email: String, name: String, phone: String): Boolean {
        var isValid = true
        val errorsList = mutableListOf<String>()
        if (phone.isEmpty() || phone.count() > 8) {
            isValid = false
            errorsList.add(resourceProvider.getString(R.string.invalid_phone))
        }
        if (name.isNullOrEmpty()) {
            isValid = false
            errorsList.add(resourceProvider.getString(R.string.name_required))
        }
        if (isValid.not())
            globalState.error(errorsList)
        return isValid
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
