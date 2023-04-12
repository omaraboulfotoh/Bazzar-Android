package com.bazzar.android.presentation.home_screen

import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.HomeSlider
import com.android.model.home.Product
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class HomeContract {

    data class State(
        val slides1: List<HomeSlider>? = emptyList(),
        val slides2: List<HomeSlider>? = emptyList(),
        val featuredCategories: List<Category>? = emptyList(),
        val featuredBrands: List<Brand>? = emptyList(),
        val categoryItems: List<Product>? = emptyList(),
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnSliderClicked(val sliderIndex: Int) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object GoToHome : Navigation()
        }
    }
}