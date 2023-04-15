package com.bazzar.android.presentation.category_screen

import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.Product
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class CategoryContract {

    data class State(
        val categoryList: List<Category>? = emptyList(),
        val brandList: List<Brand>? = emptyList(),
        val subCategoryList: List<Category>? = emptyList(),
    ) : ViewState

    sealed class Event : ViewEvent {
        data class onToggleClicked(val isCategory: Boolean) : Event()
        object onSeacrhClicked : Event()
        data class onCategoryItemClicked(val categoryItemIndex: Int) : Event()
        data class onSubCategoryItemClicked(val subCategoryItemIndex: Int) : Event()
        data class onBrandItemClicked(val brandItemIndex: Int) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToProductCategoryList(val category: Category) : Navigation()
            data class GoToProductBrandList(val brand: Brand) : Navigation()
        }
    }
}