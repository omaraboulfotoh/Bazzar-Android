package com.bazzar.android.presentation.wishlist

import com.android.model.home.BazaarModel
import com.android.model.home.Product
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class WishListContract {

    data class State(
        val productCartList: List<Product>? = emptyList(),
        val bazaarList: List<BazaarModel>? = emptyList(),
        val showEmptyBazaars: Boolean = false,
        val showEmptyProducts: Boolean = false,
        val currentPage: Int = 0
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnBackClicked : Event()
        data class OnDeleteItem(val index: Int) : Event()
        data class OnDeleteBazaar(val bazaar: BazaarModel) : Event()
        data class OnProductClicked(val index: Int) : Event()
        data class OnProductAddToCartClicked(val index: Int) : Event()
        data class OnBazaarClicked(val bazaar: BazaarModel) : Event()
        data class OnPageChanged(val pageIndex: Int) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToProduct(val product: Product) : Navigation()
            data class GoToBazaar(val bazaar: BazaarModel) : Navigation()
        }

        data class ScrollPager(val pager: Int) : Effect()
    }

}