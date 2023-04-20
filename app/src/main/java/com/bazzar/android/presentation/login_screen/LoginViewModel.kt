package com.bazzar.android.presentation.login_screen

import androidx.lifecycle.SavedStateHandle
import com.android.model.home.Product
import com.android.model.home.SearchProductRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.Constants.PRODUCT_KEY
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase, val savedStateHandle: SavedStateHandle
) :
    BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>(
        globalState
    ) {
    private val argumentProduct: Product =
        savedStateHandle.get<Product>(PRODUCT_KEY) ?: Product()

    private var isInitialized = false
    override fun setInitialState() = LoginContract.State()

    override fun handleEvents(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.OnLogin -> logIn(event.phoneNumber)
            is LoginContract.Event.OnContinueAsAGuest -> setEffect { LoginContract.Effect.Navigation.goToHomeAsGuest }
            is LoginContract.Event.OnCreateNewAccount -> setEffect { LoginContract.Effect.Navigation.goToRegisterScreen }
        }
    }

    private fun logIn(phoneNumber: String) {
        TODO("Not yet implemented")
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

