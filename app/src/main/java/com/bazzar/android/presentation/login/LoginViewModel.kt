package com.bazzar.android.presentation.login

import com.android.model.home.UserData
import com.android.model.home.UserLoginRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.utils.IResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val resoundProvider: IResourceProvider,
) : BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>(
    globalState
) {

    private var isInitialized = false
    override fun setInitialState() = LoginContract.State()

    override fun handleEvents(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.OnLogin -> logIn(
                currentState.mobileNumber,
                currentState.password
            )
            is LoginContract.Event.OnContinueAsAGuest -> setEffect { LoginContract.Effect.Navigation.GoToHomeAsGuest }
            is LoginContract.Event.OnCreateNewAccount -> setEffect { LoginContract.Effect.Navigation.GoToRegisterScreen }
        }
    }

    private fun logIn(phoneNumber: String?, password: String?) = executeCatching({

        if (phoneNumber.isNullOrEmpty().not() && password.isNullOrEmpty().not()) {
            homeUseCase.login(UserLoginRequest(phone = phoneNumber!!, password = password!!))
                .collect { loginResponse ->
                    val data = loginResponse.data!!
                    when (loginResponse) {
                        is Result.Error -> globalState.error(loginResponse.message.orEmpty())
                        is Result.Loading -> {}
                        is Result.Success -> setEffect {
                            LoginContract.Effect.Navigation.GoToHome(
                                token = data.accessToken!!,
                                userData = UserData(
                                    id = data.id,
                                    name = data.name,
                                    englishName = data.englishName,
                                    email = data.email,
                                    phone = data.phone
                                )
                            )
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

