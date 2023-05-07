package com.bazzar.android.presentation.createOrder

import com.android.local.SharedPrefersManager
import com.android.model.home.UserAddress
import com.android.model.request.CartItemRequest
import com.android.model.request.LoadCheckoutRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CreateOrderViewModel @Inject constructor(
    globalState: IGlobalState,
    private val sharedPrefersManager: SharedPrefersManager,
    private val homeUseCase: HomeUseCase
) : BaseViewModel<CreateOrderContract.Event, CreateOrderContract.State, CreateOrderContract.Effect>(
    globalState
) {

    private var isInitialized = false
    override fun setInitialState(): CreateOrderContract.State = CreateOrderContract.State()

    override fun handleEvents(event: CreateOrderContract.Event) {
        when (event) {
            CreateOrderContract.Event.OnCreateOrderClicked -> callCreateOrder()
            is CreateOrderContract.Event.OnPaymentMethodClicked -> changePaymentMethod(event.index)
            is CreateOrderContract.Event.OnNotesChanged -> setState { copy(additionalNotes = event.notes) }
            CreateOrderContract.Event.OnBackClicked -> setEffect { CreateOrderContract.Effect.Navigation.GoBack }
        }
    }

    fun init(address: UserAddress) {
        if (isInitialized.not()) {
            loadCheckout(address)
            isInitialized = true
        }
    }

    private fun loadCheckout(address: UserAddress) = executeCatching({
        val products = sharedPrefersManager.getProductList().orEmpty()
        val cartItems = products.map {
            CartItemRequest(
                it.selectedItemDetails?.id.orZero(),
                it.selectedItemDetails?.quantity.orZero()
            )
        }
        homeUseCase.loadCheckout(
            false,
            LoadCheckoutRequest(userAddressId = address.id.orZero(), cartItems = cartItems)
        )
            .collect { response ->
                when (response) {
                    is Result.Error -> globalState.error(response.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> {
                        setState {
                            copy(
                                address = address,
                                productCartList = products,
                                paymentMethodList = response.data?.paymentMethods.orEmpty(),
                                totalPrice = response.data?.totalPrice.orZero(),
                                shipping = response.data?.shipping.orZero(),
                                subTotal = response.data?.subTotal.orZero(),
                            )
                        }
                    }
                }
            }
    })

    private fun changePaymentMethod(index: Int) {

    }

    private fun callCreateOrder() {

    }
}