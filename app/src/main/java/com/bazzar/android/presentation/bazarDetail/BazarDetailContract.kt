package com.bazzar.android.presentation.bazarDetail

import com.android.model.home.BazaarModel
import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.HomeSlider
import com.android.model.home.Product
import com.android.model.request.SearchProductRequest
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState
import com.bazzar.android.presentation.homeScreen.HomeContract
import com.bazzar.android.presentation.productsList.ProductContract

class BazarDetailContract {
    data class State(
        val productList: List<Product>? = emptyList(),
        val categoriesList: List<Category> = emptyList(),
        val slider: List<HomeSlider> = emptyList(),
        val bazaar: BazaarModel? = null,
        val isFavourite: Boolean = false,
        val shareLink: String? = null,
        val hasMore: Boolean = false,
        val showEmptyView: Boolean = false,
        val isLoadingMore: Boolean = true,
        val showSuccessAddedToCart: Boolean = false,
        val searchTerm: String? = null,
        val searchRequest: SearchProductRequest = SearchProductRequest(),
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnSubCategoryClicked(val categoryIndex: Int) : Event()
        data class OnSearchTermChanged(val searchTerm: String) : Event()
        data class OnSliderClicked(val sliderItemIndex: Int) : Event()
        data class OnProductClicked(val itemIndex: Int) : Event()
        data class OnProductFavClicked(val itemIndex: Int) : Event()
        data class OnProductAddToCartClicked(val itemIndex: Int) : Event()
        object OnBackIconClicked : Event()
        object OnShareCLicked : Event()
        object OnFavouriteClicked : Event()
        object ReachedListEnd : Event()
        object OnContinueShoppingClicked : Event()
        object OnVisitYourCartClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToProductDetails(val product: Product) : Navigation()
            data class GoToBrandProductsList(val brand: Brand) : Navigation()
            data class GoToCategoryProductsList(val category: Category) : Navigation()
            object GoToBack : Navigation()
            object GoToLogin : Navigation()
            object GoToCart : Navigation()
        }

        data class OnShareBazaar(val shareText: String, val shareLink: String) : Effect()

    }

}