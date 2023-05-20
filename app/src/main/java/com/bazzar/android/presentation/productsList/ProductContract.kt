package com.bazzar.android.presentation.productsList

import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.Product
import com.android.model.home.SortFilter
import com.android.model.home.SortItem
import com.android.model.request.SearchProductRequest
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class ProductContract {
    data class State(
        val productList: List<Product>? = emptyList(),
        val brand: Brand? = null,
        val searchRequest: SearchProductRequest = SearchProductRequest(),
        val category: Category? = null,
        val subCategoryList: List<Category>? = emptyList(),
        val selectedSortItem: SortItem? = null,
        val sortFilter: SortFilter? = null,
        val listPaging: Int? = 1,
        val hasMore: Boolean = false,
        val showEmptyView: Boolean = false,
        val isLoadingMore: Boolean = true,
        val isSearchClicked: Boolean = false,
        val productScreenTitle: String = "",
        val showSortDialog: Boolean = false,
        val showFilterDialog: Boolean = false,
        val numOfSelectedFilter: Int = 0,
        val showSuccessAddedToCart: Boolean = false
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnSubCategoryClicked(val categoryIndex: Int) : Event()
        data class OnProductClicked(val itemIndex: Int) : Event()
        data class OnProductFavClicked(val itemIndex: Int) : Event()
        data class OnProductAddToCartClicked(val itemIndex: Int) : Event()
        data class OnSortItemSelected(val sortItem: SortItem) : Event()
        object OnApplySortClicked : Event()
        object OnSortClicked : Event()
        object OnFilterClicked : Event()
        object OnDismissSortDialogClicked : Event()
        object OnDismissFilterDialogClicked : Event()
        object OnBackIconClicked : Event()
        object OnSearchClicked : Event()
        object ReachedListEnd : Event()
        object OnContinueShoppingClicked : Event()
        object OnVisitYourCartClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToProductDetailPage(val product: Product) : Navigation()
            object GoToSearch : Navigation()
            object GoToLogin : Navigation()
            object GoToCart : Navigation()
            object GoToBack : Navigation()
        }

    }

}