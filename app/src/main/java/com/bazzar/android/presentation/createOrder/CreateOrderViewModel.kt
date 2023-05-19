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
            is CreateOrderContract.Event.OnPaymentCallBack -> handlePaymentSuccess(event.status)
            is CreateOrderContract.Event.OnPromoCodeChanged -> {}
            CreateOrderContract.Event.OnPromoCodeSubmit -> {}
        }
    }

    private fun handlePaymentSuccess(status: Boolean) {
        if (status) {
            setEffect { CreateOrderContract.Effect.Navigation.GoToSuccessScreen }
        } else {
            globalState.error("Payment Failure")
        }
    }

    fun init(address: UserAddress) {
        if (isInitialized.not()) {
            loadCheckout(address)
            isInitialized = true
        }
    }

    private fun loadCheckout(address: UserAddress) = executeCatching({
        homeUseCase.loadCheckout(
            LoadCheckoutRequest(userAddressId = address.id.orZero())
        )
            .collect { response ->
                when (response) {
                    is Result.Error -> globalState.error(response.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> {
                        val paymentMethodList =
                            response.data?.paymentMethods.orEmpty().toMutableList()
                        paymentMethodList[0] = paymentMethodList.first().copy(isSelected = true)
                        setState {
                            copy(
                                address = address,
                                paymentMethodList = paymentMethodList,
                                totalPrice = response.data?.totalPrice.orZero(),
                                shipping = response.data?.shipping.orZero(),
                                subTotal = response.data?.subTotal.orZero(),
                            )
                        }
                    }
                }
            }
    })

    private fun changePaymentMethod(itemIndex: Int) {
        val updated = currentState.paymentMethodList.orEmpty().mapIndexed { index, paymentMethod ->
            paymentMethod.copy(isSelected = itemIndex == index)
        }
        setState { copy(paymentMethodList = updated) }
    }

    private fun callCreateOrder() = executeCatching({

        homeUseCase.createOrder(
            LoadCheckoutRequest(
                userAddressId = currentState.address?.id.orZero(),
                paymentMethodId = currentState.paymentMethodList.orEmpty()
                    .firstOrNull { it.isSelected == true }?.id.orZero()
            )
        ).collect { response ->
            when (response) {
                is Result.Error -> globalState.error(response.message.orEmpty())
                is Result.Loading -> globalState.loading(true)
                is Result.Success -> {
                    setEffect { CreateOrderContract.Effect.Navigation.OpenWebView(response.data?.paymentURL.orEmpty()) }
                }
            }
        }
    })
}