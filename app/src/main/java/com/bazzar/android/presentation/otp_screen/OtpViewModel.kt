package com.bazzar.android.presentation.otp_screen

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
    private var isInitialized = false
    override fun setInitialState() = OtpContract.State()

    override fun handleEvents(event: OtpContract.Event) {
        when (event) {
            OtpContract.Event.OnConfirmClicked ->
                verifyOtp(
                    otpSMS = currentState.otp ?: ""
                )

            is OtpContract.Event.OnOtpChanged -> setState { copy(otp = event.otp) }
        }
    }

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
