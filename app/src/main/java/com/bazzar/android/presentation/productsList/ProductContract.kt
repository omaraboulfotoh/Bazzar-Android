package com.bazzar.android.presentation.productsList

import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.Product
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
        val listPaging: Int? = 1,
        val hasMore: Boolean = false,
        val showEmptyView: Boolean = false,
        val isLoadingMore: Boolean = true,
        val isSearchClicked: Boolean = false,
        val productScreenTitle: String = "",
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnSubCategoryClicked(val categoryIndex: Int) : Event()
        data class OnProductClicked(val itemIndex: Int) : Event()
        object OnBackIconClicked : Event()
        object OnSearchClicked : Event()
        object ReachedListEnd : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToProductDetailPage(val product: Product) : Navigation()
            object GoToSearch : Navigation()
            object GoToBack : Navigation()
        }

    }

}