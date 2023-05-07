package com.bazzar.android.presentation.homeScreen

import com.android.model.home.Brand
import com.android.model.home.BzarzModel
import com.android.model.home.Category
import com.android.model.home.HomeSlider
import com.android.model.home.Product
import com.android.model.home.ProductSection
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class HomeContract {

    data class State(
        val slides1: List<HomeSlider>? = emptyList(),
        val slides2: List<HomeSlider>? = emptyList(),
        val featuredCategories: List<Category>? = emptyList(),
        val featuredBrands: List<Brand>? = emptyList(),
        val featuredBazzars: List<BzarzModel>? = emptyList(),
        val categoryItems: List<ProductSection>? = emptyList(),
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnSliderClicked(val sliderIndex: Int, val sliderItemIndex: Int) : Event()
        data class OnBrandClicked(val index: Int) : Event()
        data class OnCategoryClicked(val index: Int) : Event()
        data class OnProductClicked(val index: Int, val sectionIndex: Int) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToProductDetails(val product: Product) : Navigation()
            data class GoToSliderPage(val slider: HomeSlider) : Navigation()
            data class GoToBrandProductsList(val brand: Brand) : Navigation()
            data class GoToCategoryProductsList(val category: Category) : Navigation()
        }
    }
}