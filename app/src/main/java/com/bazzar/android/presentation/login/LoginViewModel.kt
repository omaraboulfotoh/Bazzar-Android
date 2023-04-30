package com.bazzar.android.presentation.login

import androidx.core.text.isDigitsOnly
import com.android.local.SharedPrefersManager
import com.android.model.request.UserLoginRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val sharedPrefersManager: SharedPrefersManager,
) : BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>(
    globalState
) {
    companion object {
        const val Mobile_Number_COUNT: Int = 10
        const val MIN_PASSWORD_LENGTH: Int = 8
    }

    private var isInitialized = false
    override fun setInitialState() = LoginContract.State()

    override fun handleEvents(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.OnLogin -> {
                if (isMobileValidated(currentState.mobileNumber!!)) {
                    if (isValidPassword(currentState.password!!)) {
                        logIn(
                            currentState.mobileNumber,
                            currentState.password
                        )
                    }
                }
            }
            is LoginContract.Event.OnContinueAsAGuest -> setEffect { LoginContract.Effect.Navigation.GoToHomeAsGuest }
            is LoginContract.Event.OnCreateNewAccount -> setEffect { LoginContract.Effect.Navigation.GoToRegisterScreen }
        }
    }

    private fun isMobileValidated(mobileNumber: String): Boolean {
        return when {
            mobileNumber.length != Mobile_Number_COUNT -> false
            mobileNumber.isDigitsOnly().not() -> false
            else -> true
        }
    }

    private fun isValidPassword(password: String): Boolean {
        var containsUppercase = false
        var containsLowercase = false
        var containsNumber = false

        password.forEach {
            when {
                it.isUpperCase() -> containsUppercase = true
                it.isLowerCase() -> containsLowercase = true
                it.isDigit() -> containsNumber = true
            }
        }

        return containsUppercase && containsLowercase && containsNumber && password.length >= MIN_PASSWORD_LENGTH
    }

    private fun logIn(phoneNumber: String?, password: String?) = executeCatching({

        if (phoneNumber.isNullOrEmpty().not() && password.isNullOrEmpty().not()) {
            homeUseCase.login(UserLoginRequest(phone = phoneNumber!!, password = password!!))
                .collect { loginResponse ->
                    val userData = loginResponse.data!!
                    when (loginResponse) {
                        is Result.Error -> globalState.error(loginResponse.message.orEmpty())
                        is Result.Loading -> {}
                        is Result.Success -> {
                            sharedPrefersManager.saveToken(userData.accessToken)
                            sharedPrefersManager.saveUserData(userData)
                            setEffect { LoginContract.Effect.Navigation.GoToHome }
                        }
                        else -> {}
                    }
                }
        }
    })

    fun init() {
        if (isInitialized.not()) {
            isInitialized = true
        }
    }
}

