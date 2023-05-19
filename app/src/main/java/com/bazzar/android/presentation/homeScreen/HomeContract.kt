package com.bazzar.android.presentation.homeScreen

import com.android.model.home.Ad
import com.android.model.home.Brand
import com.android.model.home.BazaarModel
import com.android.model.home.Category
import com.android.model.home.HomeSlider
import com.android.model.home.Product
import com.android.model.home.ProductSection
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class HomeContract {

    data class State(
        val ads: List<Ad>? = emptyList(),
        val slides1: List<HomeSlider>? = emptyList(),
        val slides2: List<HomeSlider>? = emptyList(),
        val featuredCategories: List<Category>? = emptyList(),
        val featuredBrands: List<Brand>? = emptyList(),
        val featuredBazzars: List<BazaarModel>? = emptyList(),
        val categoryItems: List<ProductSection>? = emptyList(),
        val adShown: Boolean = false,
        val showSuccessAddedToCart: Boolean = false,
        val showError: Boolean = false,
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnSliderClicked(val sliderIndex: Int, val sliderItemIndex: Int) : Event()
        data class OnBrandClicked(val index: Int) : Event()
        data class OnBazaarClicked(val index: Int) : Event()
        data class OnCategoryClicked(val index: Int) : Event()
        data class OnProductClicked(val index: Int, val sectionIndex: Int) : Event()
        data class OnProductFavClicked(val index: Int, val sectionIndex: Int) : Event()
        data class OnProductAddToCartClicked(val index: Int, val sectionIndex: Int) : Event()
        object OnAdClicked : Event()
        object OnTryAgainClicked : Event()
        object OnAdDismissed : Event()
        object OnSearchClicked : Event()
        object OnShowAllCategories : Event()
        object OnShowAllBrands : Event()
        object OnShowAllBazaars : Event()
        data class OnShowAllProducts(val index: Int) : Event()

    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToProductDetails(val product: Product) : Navigation()
            data class GoToBrandProductsList(val brand: Brand) : Navigation()
            data class GoToCategoryProductsList(val category: Category) : Navigation()
            data class GoToCategoriesScreen(val showCategory: Boolean) : Navigation()
            data class GoToBazaarDetails(val bazaar: BazaarModel) : Navigation()
            object GoToBazaarsList : Navigation()
            object GoToLogin : Navigation()
            object GoToSearch : Navigation()
        }
    }
}