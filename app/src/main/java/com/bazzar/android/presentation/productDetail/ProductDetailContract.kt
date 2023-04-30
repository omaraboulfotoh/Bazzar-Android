package com.bazzar.android.presentation.productDetail

import com.android.model.home.Brand
import com.android.model.home.ItemDetail
import com.android.model.home.Product
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class ProductDetailContract {
    data class State(
        val productDetail: Product? = null,
        val selectedItemDetail: ItemDetail? = null,
        val selectedColoredImagesList: List<String> = listOf(),
        val selectedSizeTitleList: List<String> = listOf(),
        val rating: Float? = 0f,
        val isTextExpanded: Boolean = false,
    ) : ViewState

    sealed class Event : ViewEvent {
        //state
        object OnContinueShoppingClicked : Event()
        data class OnColorItemSelected(val colorIndex: Int) : Event()
        data class OnSizeItemSelected(val sizeIndex: Int) : Event()

        //navigation
        object OnVisitYourCartClicked : Event()
        object OnBackIconClicked : Event()
        object OnShareClicked : Event()
        object OnSeeMoreBrandClicked : Event()
        object OnSeeMoreClicked : Event()
        data class OnRelatedItemClicked(val itemIndex: Int) : Event()
        object OnBuyNowClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        data class ShareProduct(val product: Product) : Effect()
        sealed class Navigation : Effect() {
            object GoToCart : Navigation()
            object GoToBack : Navigation()
            data class GoToProductBrandList(val brand: Brand) : Navigation()
            data class GoToOpenProduct(val product: Product) : Navigation()
        }

    }

}