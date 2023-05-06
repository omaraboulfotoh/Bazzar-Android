package com.bazzar.android.presentation.cartScreen

import com.android.local.SharedPrefersManager
import com.android.model.home.Product
import com.android.model.request.CartItemRequest
import com.android.model.request.LoadCheckoutRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    globalState: IGlobalState,
    private val sharedPrefersManager: SharedPrefersManager,
    private val homeUseCase: HomeUseCase
) : BaseViewModel<CartContract.Event, CartContract.State, CartContract.Effect>(globalState) {

    private var isInitialized = false
    override fun setInitialState(): CartContract.State = CartContract.State()

    override fun handleEvents(event: CartContract.Event) {
        when (event) {
            CartContract.Event.OnCheckout -> handleCheckout()
            is CartContract.Event.OnDeleteItem ->
                handleItemAction(event.index, ItemOperation.DELETE)

            is CartContract.Event.OnMinusItem ->
                handleItemAction(event.index, ItemOperation.MINUS_ONE)

            is CartContract.Event.OnPlusItem ->
                handleItemAction(event.index, ItemOperation.ADD_ONE)

            is CartContract.Event.OnProductClicked ->
                handleItemAction(event.index, ItemOperation.CLICK)
        }
    }

    private fun handleItemAction(itemIndex: Int, action: ItemOperation) {
        val productsList = currentState.productCartList.orEmpty().toMutableList()
        val item = productsList[itemIndex]
        when (action) {
            ItemOperation.ADD_ONE -> if (item.selectedItemDetails?.itemBalance.orZero() > item.selectedItemDetails?.quantity.orZero()) {
                handleCartInfo(productsList.plusOne(itemIndex))
            }

            ItemOperation.MINUS_ONE -> if (item.selectedItemDetails?.quantity.orZero() > 1) {
                handleCartInfo(productsList.plusOne(itemIndex))
            } else {
                handleItemAction(itemIndex, ItemOperation.DELETE)
            }

            ItemOperation.DELETE -> {
                productsList.removeAt(itemIndex)
                handleCartInfo(productsList)
            }

            ItemOperation.CLICK -> {
                setEffect { CartContract.Effect.Navigation.GoToProduct(item) }
            }
        }
    }

    private fun handleCheckout() {
        if (sharedPrefersManager.isUserLongedIn()) {
            setEffect { CartContract.Effect.Navigation.GoToSelectAddress }
        } else {
            setEffect { CartContract.Effect.Navigation.GoToLogin }
        }
    }

    private fun startCheckout() = executeCatching({
        val cartItems = currentState.productCartList.orEmpty().map {
            CartItemRequest(
                it.selectedItemDetails?.id.orZero(),
                it.selectedItemDetails?.quantity.orZero()
            )
        }
        homeUseCase.loadCheckout(false, LoadCheckoutRequest(cartItems = cartItems))
            .collect { response ->
                when (response) {
                    is Result.Error -> globalState.error(response.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> {
                        setEffect { CartContract.Effect.Navigation.GoToSelectAddress }
                    }
                }
            }
    })

    fun init() {
        if (isInitialized.not()) {
            val productsList = sharedPrefersManager.getProductList().orEmpty()
            handleCartInfo(productsList)
            isInitialized = true
        }
    }

    private fun handleCartInfo(productsList: List<Product>) {
        val totalCount =
            productsList.sumOf { it.selectedItemDetails?.quantity.orZero() }
        val totalAmount =
            productsList.sumOf { it.selectedItemDetails?.quantity.orZero() * it.selectedItemDetails?.price.orZero() }
        setState {
            copy(
                productCartList = productsList,
                counterItem = totalCount,
                totalCartAMount = totalAmount
            )
        }
    }
}

private fun MutableList<Product>.plusOne(itemIndex: Int) =
    mapIndexed { index, product ->
        if (itemIndex == index) {
            product.copy(
                selectedItemDetails =
                product.selectedItemDetails?.copy(
                    quantity =
                    product.selectedItemDetails?.quantity.orZero() + 1
                )
            )
        } else
            product
    }

private fun MutableList<Product>.MinusOne(itemIndex: Int) =
    mapIndexed { index, product ->
        if (itemIndex == index) {
            product.copy(
                selectedItemDetails =
                product.selectedItemDetails?.copy(
                    quantity =
                    product.selectedItemDetails?.quantity.orZero() - 1
                )
            )
        } else
            product
    }

enum class ItemOperation {
    ADD_ONE,
    MINUS_ONE,
    DELETE,
    CLICK
}