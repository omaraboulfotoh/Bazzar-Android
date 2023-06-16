package com.bazzar.android.presentation.contactUs

import com.android.local.SharedPrefersManager
import com.android.model.request.ContactUsRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.R
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.contactUs.ContactUsContract.Effect
import com.bazzar.android.presentation.contactUs.ContactUsContract.Event
import com.bazzar.android.presentation.contactUs.ContactUsContract.State
import com.bazzar.android.utils.IResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject


@HiltViewModel
class ContactUsViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val sharedPrefersManager: SharedPrefersManager
) :
    BaseViewModel<Event, State, Effect>(
        globalState
    ) {

    private var isInitialized = false
    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            Event.OnBackClicked -> setEffect { Effect.Navigation.GoBack }
            is Event.OnEmailChanged -> setState { copy(email = event.email) }
            is Event.OnMessageChanged -> setState { copy(message = event.message) }
            is Event.OnNameChanged -> setState { copy(fullName = event.name) }
            is Event.OnPhoneChanged -> setState { copy(phoneNumber = event.phone) }
            is Event.OnSubjectChanged -> setState { copy(subject = event.subject) }
            Event.OnSubmitClicked -> handleSubmission()
        }
    }

    private fun handleSubmission() = executeCatching({

        val email = currentState.email.orEmpty()
        val name = currentState.fullName.orEmpty()
        val phone = currentState.phoneNumber.orEmpty()
        val subject = currentState.subject.orEmpty()
        val message = currentState.message.orEmpty()

        homeUseCase.submitContractUs(
            ContactUsRequest(
                name = name,
                email = email,
                phone = "+965$phone",
                subject = subject,
                message = message
            )
        ).collect { response ->
            when (response) {
                is Result.Error -> globalState.error(response.message.orEmpty())
                is Result.Success -> {
                    if (response.code == 0) {
                        globalState.success(R.string.contact_us_success)
                        delay(2000)
                        setEffect { Effect.Navigation.GoBack }
                    } else {
                        globalState.error(response.message.orEmpty())
                    }
                }

                else -> {}
            }

        }

    })

    fun init() {
        if (isInitialized.not()) {
            setState { copy(isArabic = sharedPrefersManager.getAppLanguage() == SharedPrefersManager.LANGUAGE_AR) }
            isInitialized = true
        }
    }
}