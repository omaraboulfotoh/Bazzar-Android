package com.bazzar.android.presentation.otp_screen

import com.android.model.home.VerifyOtpRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
) :
    BaseViewModel<OtpContract.Event, OtpContract.State, OtpContract.Effect>(
        globalState
    ) {
    private var userId: Int = -1
    private var isInitialized = false
    override fun setInitialState() = OtpContract.State()

    override fun handleEvents(event: OtpContract.Event) {
        when (event) {
            OtpContract.Event.OnConfirmClicked -> verifyOtp(otp = currentState.otp, id = userId)
            OtpContract.Event.OnSendAgainClicked -> TODO()
        }
    }

    private fun verifyOtp(otpSMS: String, id: Int) = executeCatching({
        homeUseCase.verifyOtp(VerifyOtpRequest(userId = id, otp = otpSMS))
            .collect { otpResponse ->
                when (otpResponse) {
                    is Result.Error -> globalState.error(otpResponse.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> {
                        navigateToHomeScreen(otpResponse.data)
                    }
                    else -> {}
                }
            }
    })

    private fun navigateToHomeScreen(userId: Int) {
        setEffect { OtpContract.Effect.Navigation.GoToHomeScreen(userId) }
    }


    fun init(userId: Int) {
        if (isInitialized.not()) {
            this.userId = userId
            isInitialized = true
        }
    }

}
