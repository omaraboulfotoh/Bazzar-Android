package com.bazzar.android.presentation.login_screen

import com.android.model.home.SearchProductRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.R
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
            is LoginContract.Event.OnLogin -> logIn(event.phoneNumber)
            is LoginContract.Event.OnContinueAsAGuest -> setEffect { LoginContract.Effect.Navigation.GoToHomeAsGuest }
            is LoginContract.Event.OnCreateNewAccount -> setEffect { LoginContract.Effect.Navigation.GoToRegisterScreen }
        }
    }

    private fun logIn(phoneNumber: String) {

        // todo will handle the validation
//        if (phoneNumber.notMatdch) {
//            setState { copy(phoneNumberError = resoundProvider.getString(R.string.success)) }
//        }
    }

    fun init() {
        if (isInitialized.not()) {
            setState {
                copy(
//                    relatedProductList =product.
                )
            }
//            loadProductData()
            isInitialized = true
        }
    }

    fun isPhoneValid() {

    }

    private fun loadProductData(categoryId: Int) = executeCatching({
        homeUseCase.getAllProductList(SearchProductRequest(categoryId = categoryId))
            .collect { productResponse ->
                when (productResponse) {
                    is Result.Error -> globalState.error(productResponse.message.orEmpty())
                    is Result.Loading -> {}
                    is Result.Success -> setState {
                        copy(
                        )
                    }

                    else -> {}
                }
            }
    })
}

