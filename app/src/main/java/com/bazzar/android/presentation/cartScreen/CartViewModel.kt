package com.bazzar.android.presentation.cartScreen

import com.android.local.SharedPrefersManager
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    globalState: IGlobalState,
    private val sharedPrefersManager: SharedPrefersManager
) : BaseViewModel<CartContract.Event, CartContract.State, CartContract.Effect>(globalState) {
    override fun setInitialState(): CartContract.State = CartContract.State()

    override fun handleEvents(event: CartContract.Event) {
    }

    fun init() {
        val productsList = sharedPrefersManager.getProductList()
        val totalCount = productsList.orEmpty().sumOf { it.selectedItemDetails?.quantity.orZero() }
        setState { copy(productCartList = productsList, counterItem = productsList?.size) }
    }


}