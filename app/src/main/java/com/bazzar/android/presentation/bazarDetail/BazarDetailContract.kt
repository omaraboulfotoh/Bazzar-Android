package com.bazzar.android.presentation.bazarDetail

import com.android.model.home.BazaarModel
import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.Filter
import com.android.model.home.HomeSlider
import com.android.model.home.Product
import com.android.model.home.Sort
import com.android.model.home.SortFilter
import com.android.model.request.SearchProductRequest
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState
import com.bazzar.android.presentation.homeScreen.HomeContract
import com.bazzar.android.presentation.productsList.ProductContract
import com.bazzar.android.presentation.productsList.composables.filter.FilterType

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
        val showSortDialog: Boolean = false,
        val showFilterDialog: Boolean = false,
        val numOfSelectedFilter: Int = 0,
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
        data class OnSearchTermChanged(val searchTerm: String) : Event()
        data class OnSearchClicked(val term: String? = null) : Event()
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