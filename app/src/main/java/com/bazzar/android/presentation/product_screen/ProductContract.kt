package com.bazzar.android.presentation.product_screen

import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.Product
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class ProductContract {
    data class State(
        var filteredProductList: List<Product>? = emptyList(),
        val productList: List<Product>? = emptyList(),
        val brand: Brand? = Brand(),
        val subCategoryList: List<Category>? = emptyList(),
        val selectedSubCategoryList: List<Category>? = emptyList(),
        val isSubCategoryClicked: Boolean = false,
        val isSearchClicked: Boolean = false,
        val isSortClicked: Boolean = false,
        val ProductScreenTitle: String = "",
        val sortedValues: SortingValues = SortingValues.NO_SORTING
    ) : ViewState {
        enum class SortingValues {
            NO_SORTING,
            PRICE_HIGH_TO_LOW,
            PRICE_LOW_TO_HEIGH,
            DISCOUNT_ONLY,
            NEW_ARRIVAL,
            BAZAAAR_PICKS
        }
    }

    sealed class Event : ViewEvent {
        data class OnFavouriteClicked(val productIndex: Int, val sliderItemIndex: Int) : Event()
        data class OnAddToCartClicked(val productIndex: Int, val sliderItemIndex: Int) : Event()
        data class OnSubCategoryClicked(val categoryIndex: Int) : Event()
        data class onSortItemSelected(val sortItem:State.SortingValues=State.SortingValues.NO_SORTING) : Event()
        object OnSortClicked : Event()
        object OnFilterClicked : Event()
        object OnBackClicked : Event()
        object OnSearchClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToProductDetailPage(val product: Product) : Navigation()
            object back : Navigation()
        }

    }

}