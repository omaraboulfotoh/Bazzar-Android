package com.bazzar.android.presentation.cartScreen

import com.android.model.home.Product
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class CartContract {

    data class State(
        val productWishList: List<Product>? = emptyList(),
        val productCartList: List<Product>? = emptyList(),
        var counterItem: Int? = null
    ) : ViewState

    sealed class Event : ViewEvent {
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
        }
    }

}
