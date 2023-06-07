package com.bazzar.android.presentation.search

import com.android.model.home.Product
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class SearchScreenContract {
    data class State(
        val searchTerm: String? = null,
        val recentSearchList: List<String>? = listOf(),
        val productList: List<Product>? = emptyList(),
        val showSuccessAddedToCart: Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnRecentSearchClicked(val searchTerm: String) : Event()
        data class OnRemoveRecentSearchClicked(val index: Int, val searchTerm: String) : Event()
        data class OnSearchTermChanged(val searchTerm: String) : Event()
        data class OnProductClicked(val index: Int) : Event()
        data class OnRelatedItemFavClicked(val index: Int) : Event()
        data class OnProductAddToCartClicked(val index: Int) : Event()
        object OnSearchClicked : Event()
        object OnContinueShoppingClicked : Event()
        object OnVisitYourCartClicked : Event()
        object OnBackClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToProductScreen(val searchTerm: String) : Navigation()
            data class GoToProductDetails(val product: Product) : Navigation()
            object GoBack : Navigation()
            object GoToLogin : Navigation()
        }
    }
}