package com.bazzar.android.presentation.login

import com.android.local.SharedPrefersManager
import com.android.model.request.GuestLoginRequest
import com.android.model.request.UserLoginRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val sharedPrefersManager: SharedPrefersManager,
) : BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>(
    globalState
) {

    private var isInitialized = false
    override fun setInitialState() = LoginContract.State()

    override fun handleEvents(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.OnLogin -> logIn()
            is LoginContract.Event.OnContinueAsAGuest -> guestLogin()
            is LoginContract.Event.OnCreateNewAccount -> setEffect { LoginContract.Effect.Navigation.GoToRegisterScreen }
            is LoginContract.Event.OnPasswordChanged -> setState { copy(password = event.password) }
            is LoginContract.Event.OnPhoneChanged -> setState { copy(mobileNumber = event.phoneNumber) }
        }
    }

    private fun guestLogin() = executeCatching({

        homeUseCase.loginGuest(
            GuestLoginRequest(
                deviceId = "",
                accessToken = sharedPrefersManager.getFcmToken().orEmpty()
            )
        ).collect { loginResponse ->
            when (loginResponse) {
                is Result.Error -> globalState.error(loginResponse.message.orEmpty())
                is Result.Loading -> globalState.loading(true)
                is Result.Success -> {
                    // update fcm token on the api
                    sharedPrefersManager.saveToken(loginResponse.data?.accessToken)
                    sharedPrefersManager.saveUserData(loginResponse.data!!)
                    setEffect { LoginContract.Effect.Navigation.GoBack }
                }

                else -> {}
            }
        }
    })

    private fun logIn() = executeCatching({

        homeUseCase.login(
            UserLoginRequest(
                "+965${currentState.mobileNumber.orEmpty()}",
                currentState.password.orEmpty()
            )
        ).collect { loginResponse ->
            when (loginResponse) {
                is Result.Error -> globalState.error(loginResponse.message.orEmpty())
                is Result.Loading -> globalState.loading(true)
                is Result.Success -> {
                    // update fcm token on the api

                    updateFCM()
                    sharedPrefersManager.saveToken(loginResponse.data?.accessToken)
                    sharedPrefersManager.saveUserData(loginResponse.data!!)
                    setEffect { LoginContract.Effect.Navigation.GoBack }
                }

                else -> {}
            }
        }
    })

    suspend fun updateFCM() {
        GlobalScope.launch {
            sharedPrefersManager.getFcmToken()?.let {
                homeUseCase.updateFcmToken(it)
            }
        }
    }

    fun init() {
        if (isInitialized.not()) {
            isInitialized = true
        }
    }
}

