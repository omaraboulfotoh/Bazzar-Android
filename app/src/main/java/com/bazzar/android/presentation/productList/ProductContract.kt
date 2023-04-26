package com.bazzar.android.presentation.productList

import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.Product
import com.android.model.home.SearchProductRequest
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
        val isSearchClicked: Boolean = false,
        val ProductScreenTitle: String = "",
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnSubCategoryClicked(val categoryIndex: Int) : Event()
        object OnBackIconClicked : Event()
        object OnSearchClicked : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToProductDetailPage(val product: Product) : Navigation()
            object GoToBack : Navigation()
        }

    }

}