package com.bazzar.android.presentation.createOrder

import com.android.model.home.UserAddress
import com.android.model.request.LoadCheckoutRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.R
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.utils.IResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject


@HiltViewModel
class CreateOrderViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val resourceProvider: IResourceProvider
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
            is CreateOrderContract.Event.OnPromoCodeChanged -> setState { copy(promoCode = event.promoCode) }
            CreateOrderContract.Event.OnPromoCodeSubmit -> submitPromoCode()
        }
    }

    private fun submitPromoCode() {
        val promoCode = currentState.promoCode.orEmpty().trim()
        if (promoCode.isEmpty().not()) {
            loadCheckout(currentState.address!!, promoCode = promoCode)
        }
    }

    private fun handlePaymentSuccess(status: Boolean) = executeCatching({
        if (status) {
            homeUseCase.clearCart().collect {
                when (it) {
                    is Result.Success -> {
                        setEffect { CreateOrderContract.Effect.Navigation.GoToSuccessScreen }
                    }

                    else -> {
                        setEffect { CreateOrderContract.Effect.Navigation.GoToSuccessScreen }
                    }
                }
            }
        } else {
            globalState.error(resourceProvider.getString(R.string.payment_falid))
        }
    })

    fun init(address: UserAddress) {
        if (isInitialized.not()) {
            loadCheckout(address)
            isInitialized = true
        }
    }

    private fun loadCheckout(address: UserAddress, promoCode: String? = null) = executeCatching({
        homeUseCase.loadCheckout(
            LoadCheckoutRequest(
                userAddressId = address.id.orZero(),
                promotionCode = promoCode,
                orderNotes = currentState.additionalNotes
            )
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
                                discount = response.data?.discount.orZero(),
                                promoCode = null,
                                orderPromoCode = promoCode
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
                promotionCode = currentState.orderPromoCode,
                paymentMethodId = currentState.paymentMethodList.orEmpty()
                    .firstOrNull { it.isSelected == true }?.id.orZero(),
                orderNotes = currentState.additionalNotes
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