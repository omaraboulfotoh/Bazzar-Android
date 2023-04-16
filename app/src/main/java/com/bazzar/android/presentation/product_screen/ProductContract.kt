package com.bazzar.android.presentation.product_screen

import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.Product
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class ProductContract {
    data class State(
        val productList: List<Product>? = emptyList(),
        val brand: Brand?,
        val subCategoryList: List<Category>? = emptyList(),
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnFavouriteClicked(val productIndex: Int, val sliderItemIndex: Int) : Event()
        data class OnAddToCartClicked(val productIndex: Int, val sliderItemIndex: Int) : Event()
        data class OnSortClicked(val productIndex: Int, val sliderItemIndex: Int) : Event()
        data class OnFilterClicked(val productIndex: Int, val sliderItemIndex: Int) : Event()
        object OnBackClicked:Event()
        object OnSearchClicked:Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToProductDetailPage(val product: Product) : Navigation()
        }
    }

}