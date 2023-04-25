package com.bazzar.android.presentation.homeScreen

import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
) :
    BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>(
        globalState
    ) {

    private var isInitialized = false
    override fun setInitialState(): HomeContract.State {
        return HomeContract.State()
    }

    override fun handleEvents(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnSliderClicked -> handleSliderAction(
                event.sliderIndex,
                event.sliderItemIndex
            )

            is HomeContract.Event.OnBrandClicked -> handleBrandClicked(event.index)
            is HomeContract.Event.OnCategoryClicked -> TODO()
        }
    }

    private fun handleBrandClicked(index: Int) {
        val brand = currentState.featuredBrands?.get(index) ?: return
        setEffect { HomeContract.Effect.Navigation.GoToBrandProductsList(brand) }
    }

    private fun handleCategoryClicked(index: Int) {
        val category = currentState.featuredCategories?.get(index) ?: return
        setEffect { HomeContract.Effect.Navigation.GoToCategoryProductsList(category) }
    }

    private fun handleSliderAction(sliderIndex: Int, sliderItemIndex: Int) {
        val sliderList =
            (if (sliderIndex == 0) currentState.slides1 else currentState.slides2) ?: return
        val selectedItem = sliderList[sliderItemIndex]
        setEffect { HomeContract.Effect.Navigation.GoToSliderPage(selectedItem) }
    }

    fun init() {
        if (isInitialized.not()) {
            loadHomeData()
            isInitialized = true
        }
    }

    private fun loadHomeData() = executeCatching({
        homeUseCase.getHome().collect {
            when (it) {
                is Result.Error -> globalState.error(it.message.orEmpty())
                is Result.Loading -> {}
                is Result.Success -> setState {
                    copy(
                        slides1 = it.data?.slider1,
                        slides2 = it.data?.slider2,
                        categoryItems = it.data?.categoryItems,
                        featuredBrands = it.data?.featuredBrands,
                        featuredCategories = it.data?.featuredCategories
                    )
                }

                else -> {}
            }
        }
    })

}