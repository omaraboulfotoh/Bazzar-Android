package com.bazzar.android.presentation.productsList

import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.Filter
import com.android.model.home.Product
import com.android.model.home.SortFilter
import com.android.model.home.Sort
import com.android.model.request.SearchProductRequest
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState
import com.bazzar.android.presentation.productsList.composables.filter.FilterType

class ProductContract {
    data class State(
        val productList: List<Product>? = emptyList(),
        val brand: Brand? = null,
        val searchRequest: SearchProductRequest = SearchProductRequest(),
        val category: Category? = null,
        val subCategoryList: List<Category>? = emptyList(),
        val listPaging: Int? = 1,
        val hasMore: Boolean = false,
        val showEmptyView: Boolean = false,
        val isLoadingMore: Boolean = true,
        val isSearchClicked: Boolean = false,
        val productScreenTitle: String = "",
        val showSortDialog: Boolean = false,
        val showFilterDialog: Boolean = false,
        val numOfSelectedFilter: Int = 0,
        val showSuccessAddedToCart: Boolean = false,
        val selectedFilterType: FilterType? = null,
        val selectedFilterMaxPrice: Int? = null,
        val selectedFilterMinPrice: Int? = null,
        val filterListToShow: List<Filter>? = null,
        val numOfSelectedCategoryFilters: Int = 0,
        val numOfSelectedBrandFilters: Int = 0,
        val numOfSelectedColorFilters: Int = 0,
        val numOfSelectedSizeFilters: Int = 0,
        val categoryFilterList: List<Filter>? = null,
        val brandFilterList: List<Filter>? = null,
        val colorFilterList: List<Filter>? = null,
        val sizeFilterList: List<Filter>? = null,
        val selectedSort: Sort? = null,
        val sortFilter: SortFilter? = null,
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnSubCategoryClicked(val categoryIndex: Int) : Event()
        data class OnProductClicked(val itemIndex: Int) : Event()
        data class OnProductFavClicked(val itemIndex: Int) : Event()
        data class OnProductAddToCartClicked(val itemIndex: Int) : Event()
        data class OnSortItemSelected(val sort: Sort) : Event()
        data class OnFilterTypeClicked(val filterType: FilterType) : Event()
        data class OnSelectUnselectFilter(val filter: Filter, val isSelect: Boolean) : Event()
        data class OnMaxPriceChanged(val maxPrice: Int) : Event()
        data class OnMinPriceChanged(val minPrice: Int) : Event()
        object OnApplyFiltersClicked : Event()
        object OnResetFiltersClicked : Event()
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