package com.bazzar.android.presentation.productDetail

import com.android.model.home.BazaarModel
import com.android.model.home.Brand
import com.android.model.home.ItemDetail
import com.android.model.home.ItemImages
import com.android.model.home.Product
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class ProductDetailContract {
    data class State(
        val productDetail: Product? = null,
        val selectedItemDetail: ItemDetail? = null,
        val selectedColoredImagesList: List<String> = listOf(),
        val sizeTitleList: List<ItemDetail> = listOf(),
        val colorsList: List<ItemImages> = listOf(),
        val rating: Float? = 0f,
        val isFavourite: Boolean = false,
        val isTextExpanded: Boolean = false,
        val showSuccessAddedToCart: Boolean = false,
        val bazaar: BazaarModel? = null
    ) : ViewState

    sealed class Event : ViewEvent {
        //state
        data class OnColorItemSelected(val colorIndex: Int) : Event()
        data class OnSizeItemSelected(val sizeIndex: Int) : Event()
        data class OnImageClicked(val index: Int) : Event()

        //navigation
        object OnContinueShoppingClicked : Event()
        object OnVisitYourCartClicked : Event()
        object OnBackIconClicked : Event()
        object OnShareClicked : Event()
        object OnFavClicked : Event()
        object OnSeeMoreBrandClicked : Event()
        object OnSeeMoreClicked : Event()
        data class OnRelatedItemClicked(val itemIndex: Int) : Event()
        data class OnRelatedItemFavClicked(val itemIndex: Int) : Event()
        data class OnProductAddToCartClicked(val itemIndex: Int) : Event()
        object OnBuyNowClicked : Event()
        object OnTackToUsClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        data class ShareProduct(val shareText: String, val shareLink: String) : Effect()
        sealed class Navigation : Effect() {
            object GoToCart : Navigation()
            object GoToLogin : Navigation()
            object GoToBack : Navigation()
            data class GoToTalkToUs(val whatsAppNumber: String) : Navigation()
            data class GoToImageViewer(
                val imagePathList: List<String>,
                val product: Product?,
                val showBuyButton: Boolean
            ) :
                Navigation()

            data class GoToProductBrandList(val brand: Brand) : Navigation()
            data class GoToOpenProduct(val product: Product) : Navigation()
        }

    }

}