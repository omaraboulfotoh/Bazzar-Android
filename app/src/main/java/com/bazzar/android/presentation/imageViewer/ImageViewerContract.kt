package com.bazzar.android.presentation.imageViewer

import com.android.model.home.BazaarModel
import com.android.model.home.Product
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class ImageViewerContract {
    data class State(
        val imagePathsList: List<String> = emptyList(),
        val product: Product? = null,
        val currentIndicatorIndex: Int = 0,
        val showSuccessAddedToCart: Boolean = false,
        val bazaar: BazaarModel? = null,
        val showBuyButton :Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnIndicatorClicked(val index: Int) : Event()
        object OnBuyNowClicked : Event()
        object OnContinueShoppingClicked : Event()
        object OnVisitYourCartClicked : Event()
        object OnBackClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object GoToCart : Navigation()
            object GoToLogin : Navigation()
            object GoBack : Navigation()
        }
    }
}