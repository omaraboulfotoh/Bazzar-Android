package com.bazzar.android.presentation.productDetail

import com.android.model.home.Brand
import com.android.model.home.Product
import com.android.model.home.ProductDetail
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class ProductDetailContract {
    data class State(
        val productDetail: ProductDetail? = ProductDetail(),
        val selectedItemDetailId: Int? = null,
        val selectedColorId: Int? = null,
        val selectedColoredImagesList: List<String> = listOf(),
        val selectedSizeTitleList: List<String> = listOf(),
        val rating: Float? = 0f,
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
        object OnSeeMoreClicked : Event()
        data class OnRelatedItemClicked(val itemIndex: Int) : Event()
        object OnBuyNowClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        data class ShareProduct(val product: ProductDetail) : Effect()
        sealed class Navigation : Effect() {
            object GoToCart : Navigation()
            object GoToBack : Navigation()
            data class GoToProductBrandList(val brand: Brand) : Navigation()
            data class GoToOpenProduct(val product: Product) : Navigation()
        }

    }

}