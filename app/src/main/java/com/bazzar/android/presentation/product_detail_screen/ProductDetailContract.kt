package com.bazzar.android.presentation.productList

import com.android.model.home.Brand
import com.android.model.home.Product
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class ProductDetailContract {
    data class State(
        var product: Product = Product(),
        var brand: Brand = Brand(),
        val relatedProductList: List<Product>? = emptyList(),
        val isBuyNowClicked: Boolean = false,
        val isContinueShoppingClicked: Boolean = false,
        val isFavouriteIconClicked: Boolean = false,
        val rating:Int=0
    ) : ViewState

    sealed class Event : ViewEvent {
        //state
        object OnBuyNowClicked : Event()
        object OnContinueShoppingClicked : Event()
        object OnFavouriteIconClicked : Event()
        data class OnSliderClicked(val sliderIndex: Int, val sliderItemIndex: Int) : Event()
        data class OnRatingClicked(val ratingIndex: Int) : Event()

        //navigation
        object OnVisitYourCartClicked : Event()
        object OnBackIconClicked : Event()
        object OnShareClicked : Event()
        object OnSeeMoreClicked : Event()

    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object goToCart : Navigation()
            object back : Navigation()
            object Share : Navigation()
            data class goToProductBrandList(val brandId: Int) : Navigation()
            data class GoToSliderPage(val product: Product) : Navigation()

        }

    }

}