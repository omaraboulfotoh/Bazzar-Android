package com.bazzar.android.presentation.otp_screen

import androidx.core.text.isDigitsOnly
import com.android.local.SharedPrefersManager
import com.android.model.home.UserData
import com.android.model.request.VerifyOtpRequest
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
    private val sharedPrefersManager: SharedPrefersManager,
) : BaseViewModel<OtpContract.Event, OtpContract.State, OtpContract.Effect>(
    globalState
) {
    companion object {
        const val OTP_SIZE: Int = 4
    }

    private var isInitialized = false
    override fun setInitialState() = OtpContract.State()

    override fun handleEvents(event: OtpContract.Event) {
        when (event) {
            is OtpContract.Event.OnConfirmClicked -> {
                val otp = currentState.otp ?: ""
                if (isInputDataValidated(otp)) {
                    verifyOtp(otpSMS = otp)
                }
            }
            is OtpContract.Event.OnOtpChanged -> setState { copy(otp = event.otp) }
            is OtpContract.Event.OnSendAgain -> resendOtp()
        }
    }

    private fun isInputDataValidated(userEnteredOtp: String): Boolean {
        return when {
            userEnteredOtp.length != OTP_SIZE -> {
                // error message wrong size
                false
            }
            !userEnteredOtp.isDigitsOnly() -> {
                // error message not digit
                false
            }
            else -> true
        }
    }

    private fun resendOtp() = executeCatching({
        val id = currentState.userData?.id ?: return@executeCatching
        homeUseCase.resendOtp(userId = id)
            .collect { resendReponse ->
                when (resendReponse) {
                    is Result.Error -> globalState.error(resendReponse.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> { }
                }
            }
    })

    private fun verifyOtp(otpSMS: String) = executeCatching({
        // todo add validation on OTP
        val id = currentState.userData?.id ?: return@executeCatching
        homeUseCase.verifyOtp(VerifyOtpRequest(userId = id, otp = otpSMS))
            .collect { otpResponse ->
                when (otpResponse) {
                    is Result.Error -> globalState.error(otpResponse.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> {
                        val userData = otpResponse.data!!
                        sharedPrefersManager.saveToken(userData.accessToken)
                        sharedPrefersManager.saveUserData(userData)
                        setEffect { OtpContract.Effect.Navigation.GoToHomeScreen }
                    }

                    else -> {}
                }
            }
    })

    fun init(userData: UserData) {
        if (isInitialized.not()) {
            setState { copy(userData = userData) }
            isInitialized = true
        }
    }
}
