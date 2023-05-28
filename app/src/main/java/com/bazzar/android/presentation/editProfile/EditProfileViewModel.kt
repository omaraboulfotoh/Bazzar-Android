package com.bazzar.android.presentation.editProfile

import android.util.Patterns
import com.android.local.SharedPrefersManager
import com.android.model.home.UserData
import com.android.model.request.UserRegisterRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.R
import com.bazzar.android.common.remove
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.utils.IResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val sharedPrefersManager: SharedPrefersManager,
    private val resourceProvider: IResourceProvider
) :
    BaseViewModel<EditProfileContract.Event, EditProfileContract.State, EditProfileContract.Effect>(
        globalState
    ) {

    private var isInitialized = false
    override fun setInitialState() = EditProfileContract.State()

    override fun handleEvents(event: EditProfileContract.Event) {
        when (event) {
            is EditProfileContract.Event.OnEmailChanged -> setState { copy(email = event.email) }
            is EditProfileContract.Event.OnNameChanged -> setState { copy(fullName = event.name) }
            is EditProfileContract.Event.OnEditAccountClicked -> handleEditAccountClicked()
            is EditProfileContract.Event.OnBackClicked -> setEffect { EditProfileContract.Effect.Navigation.GoBack }
            is EditProfileContract.Event.OnChangePasswordClicked ->
                setEffect { EditProfileContract.Effect.Navigation.GoToChangePassword }
        }
    }

    private fun handleEditAccountClicked() {
        val name = currentState.fullName.orEmpty()
        val email = currentState.email.orEmpty()
        if (isValid(email, name)) {
            val userRegisterRequest = UserRegisterRequest(
                id = sharedPrefersManager.getUserData()?.id,
                name = name,
                englishName = name,
                email = email,
                phone = "+965${currentState.phoneNumber.orEmpty()}"
            )
            editProfile(userRegisterRequest)
        }
    }

    private fun isValid(email: String, name: String): Boolean {
        var isValid = true
        val errorsList = mutableListOf<String>()
        if (name.isNullOrEmpty()) {
            isValid = false
            errorsList.add(resourceProvider.getString(R.string.name_required))
        }
        if (email.isNullOrEmpty().not() && Patterns.EMAIL_ADDRESS.matcher(email).matches().not()) {
            isValid = false
            errorsList.add(resourceProvider.getString(R.string.invalid_email))
        }
        if (isValid.not())
            globalState.error(errorsList)
        return isValid
    }


    fun init(userData: UserData) {
        if (isInitialized.not()) {
            isInitialized = true
            setState {
                copy(
                    phoneNumber = userData.phone.orEmpty().remove("+965"),
                    fullName = userData.name,
                    email = userData.email,
                )
            }
        }
    }

    private fun editProfile(request: UserRegisterRequest) = executeCatching({
        homeUseCase.editProfile(request)
            .collect { response ->
                when (response) {
                    is Result.Error -> globalState.error(response.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> {
                        response.data?.let { editedUserData ->
                            val cachedUserData = sharedPrefersManager.getUserData()?.copy(
                                name = editedUserData.name,
                                englishName = editedUserData.englishName,
                                email = editedUserData.email,
                            )
                            cachedUserData?.let {
                                sharedPrefersManager.saveUserData(cachedUserData)
                            }
                        }
                        setEffect { EditProfileContract.Effect.Navigation.GoBack }
                    }
                }
            }
    })
}
