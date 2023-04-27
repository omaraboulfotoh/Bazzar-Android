package com.bazzar.android.presentation.product_detail_screen

import com.android.model.home.ItemDetail
import com.android.model.home.ProductDetail
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class ProductDetailContract {
    data class State(
        var productDetail: ProductDetail = ProductDetail(),
        var selectedItemDetailId: Int = -1,
        var selectedColorId: Int = -1,
        var selectedColoredImagesList: List<String>? = listOf(),
        var selectedSizeTitleList: List<String> = listOf(),
        val isBuyNowClicked: Boolean = false,
        val isContinueShoppingClicked: Boolean = false,
        val isFavouriteIconClicked: Boolean = false,
        val rating: Int = 0,
        val isColorItemClicked:Boolean=false,
        val isSizeClicked:Boolean=false,
    ) : ViewState

    sealed class Event : ViewEvent {
        //state
        object OnContinueShoppingClicked : Event()
        object OnFavouriteIconClicked : Event()
        data class OnSliderClicked(val sliderIndex: Int, val sliderItemIndex: Int) : Event()
        data class OnRatingClicked(val ratingIndex: Int) : Event()
        data class OnColorItemSelected(val colorIndex: Int) : Event()
        data class OnSizeItemSelected(val sizeIndex: Int) : Event()

        //navigation
        object OnVisitYourCartClicked : Event()
        object OnBackIconClicked : Event()
        object OnShareClicked : Event()
        object OnSeeMoreClicked : Event()
        data class OnRelatedItemClicked(val itemDetailId: Int) : Event()
        data class OnBuyNowClicked(val itemDetailId: Int) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object goToCart : Navigation()
            object back : Navigation()
            object Share : Navigation()
            data class goToProductBrandList(val brandId: Int) : Navigation()
            data class GoToSliderPage(val product: ProductDetail) : Navigation()

        }

    }

}