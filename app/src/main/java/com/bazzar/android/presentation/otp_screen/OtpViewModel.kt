package com.bazzar.android.presentation.otp_screen

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
) : BaseViewModel<OtpContract.Event, OtpContract.State, OtpContract.Effect>(
    globalState
) {
    private var userData: UserData = UserData()
    private var isInitialized = false
    override fun setInitialState() = OtpContract.State()

    override fun handleEvents(event: OtpContract.Event) {
        when (event) {
            OtpContract.Event.OnConfirmClicked -> verifyOtp(
                otpSMS = currentState.otp ?: "",
                id = userData.id ?: -1
            )
        }
    }

    private fun verifyOtp(otpSMS: String, id: Int) = executeCatching({
        homeUseCase.verifyOtp(VerifyOtpRequest(userId = id, otp = otpSMS))
            .collect { otpResponse ->
                when (otpResponse) {
                    is Result.Error -> globalState.error(otpResponse.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> {
                        val data = otpResponse.data!!
                        setEffect {
                            OtpContract.Effect.Navigation.GoToHomeScreen(
                                userData = data
                            )
                        }

                    }

                    else -> {}
                }
            }
    })

    fun init(userData: UserData) {
        if (isInitialized.not()) {
            this.userData = userData
            isInitialized = true
        }
    }

}
