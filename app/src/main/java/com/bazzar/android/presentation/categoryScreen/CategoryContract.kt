package com.bazzar.android.presentation.categoryScreen

import com.android.model.home.Brand
import com.android.model.home.Category
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class CategoryContract {

    data class State(
        val categoryList: List<Category>? = emptyList(),
        val mainCategorisesList: List<Category>? = emptyList(),
        val subCategoriesList: List<Category>? = emptyList(),
        val showCategories: Boolean = true,
        val brandList: List<Brand>? = emptyList(),
        val brandListToShow: List<Brand>? = emptyList(),
        val searchBrandTerm: String? = null,
        val isSearchBrandOpen: Boolean = false,
    ) : ViewState

    sealed class Event : ViewEvent {
        object OnToggleClicked : Event()
        object OnSearchClicked : Event()
        object OnDismissClicked : Event()
        object OnSearchBrandClicked : Event()
        object OnCancelSearchBrandClicked : Event()
        data class OnCategoryItemClicked(val categoryItemIndex: Int) : Event()
        data class OnSubCategoryItemClicked(val subCategoryItemIndex: Int) : Event()
        data class OnBrandItemClicked(val brandItemIndex: Int) : Event()
        data class OnSearchBrandChanged(val term: String) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToProductCategoryList(val category: Category) : Navigation()
            data class GoToProductBrandList(val brand: Brand) : Navigation()
            object GoToSearch : Navigation()
        }
    }
}