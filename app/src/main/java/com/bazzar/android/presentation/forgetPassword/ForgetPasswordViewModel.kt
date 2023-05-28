package com.bazzar.android.presentation.forgetPassword

import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.R
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.forgetPassword.ForgetPasswordContract.Effect
import com.bazzar.android.presentation.forgetPassword.ForgetPasswordContract.Event
import com.bazzar.android.presentation.forgetPassword.ForgetPasswordContract.State
import com.bazzar.android.utils.IResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val resourceProvider: IResourceProvider
) :
    BaseViewModel<Event, State, Effect>(
        globalState
    ) {


    private var isInitialized = false

    override fun setInitialState() = State()
    override fun handleEvents(event: Event) {
        when (event) {
            Event.OnBackClicked -> setEffect { Effect.Navigation.GoBack }
            is Event.OnPhoneChanged -> setState { copy(phoneNumber = event.phone) }
            Event.OnSendClicked -> requestForgetPassword()
        }
    }

    private fun requestForgetPassword() = executeCatching({
        val phone = currentState.phoneNumber
        if (phone?.count() != 8 || phone.matches(Regex("\\d+")).not()) {
            globalState.error(resourceProvider.getString(R.string.invalid_phone))
            return@executeCatching
        }
        homeUseCase.requestForgetPassword("+965$phone").collect {
            when (it) {
                is Result.Error -> globalState.error(it.message.orEmpty())
                is Result.Success -> {
                    if (it.code != 0) {
                        globalState.error(it.message.orEmpty())
                    } else {
                        globalState.success(resourceProvider.getString(R.string.forget_password_success))
                        delay(1000)
                        setEffect { Effect.Navigation.GoBack }
                    }
                }

                is Result.Loading -> globalState.loading(true)
            }
        }

    })

    fun init(phoneNumber: String) {
        if (isInitialized.not()) {
            setState { copy(phoneNumber = phoneNumber) }
            isInitialized = true
        }
    }

}
