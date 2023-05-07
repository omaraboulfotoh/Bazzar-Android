package com.bazzar.android.presentation.cartScreen

import com.android.model.home.Product
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class CartContract {

    data class State(
        val productCartList: List<Product>? = emptyList(),
        val counterItem: Int? = null,
        val totalCartAMount: Double = 0.0,
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnCheckout : Event()
        data class OnDeleteItem(val index: Int) : Event()
        data class OnPlusItem(val index: Int) : Event()
        data class OnMinusItem(val index: Int) : Event()
        data class OnProductClicked(val index: Int) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToProduct(val product: Product) : Navigation()
            object GoToLogin : Navigation()
            object GoToSelectAddress : Navigation()
        }
    }

}
