package com.bazzar.android.presentation.homeScreen

import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.Product
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

            is HomeContract.Event.OnSearchClicked -> setEffect { HomeContract.Effect.Navigation.GoToSearch }
            is HomeContract.Event.OnBrandClicked -> handleBrandClicked(event.index)
            is HomeContract.Event.OnCategoryClicked -> handleCategoryClicked(event.index)
            is HomeContract.Event.OnProductClicked -> onProductClicked(
                event.index,
                event.sectionIndex
            )

            HomeContract.Event.OnAdClicked -> handleAdClicked()
            HomeContract.Event.OnShowAllBazaars -> setEffect { HomeContract.Effect.Navigation.GoToBazaarsList }
            HomeContract.Event.OnShowAllBrands -> navigateToCategoryTab(false)
            HomeContract.Event.OnShowAllCategories -> navigateToCategoryTab(true)
            is HomeContract.Event.OnShowAllProducts -> handleProductNavigation(event.index)
            is HomeContract.Event.OnBazaarClicked -> navigateToBazaar(event.index)
            HomeContract.Event.OnAdDismissed -> handleAdDismiss()
            HomeContract.Event.OnTryAgainClicked -> handleTryAgain()
        }
    }

    private fun handleTryAgain() {
        setState { copy(showError = false) }
        loadHomeData()
    }

    private fun handleAdDismiss() {
        setState { copy(adShown = true) }
    }

    private fun navigateToBazaar(index: Int) {
        val bazaar = currentState.featuredBazzars.orEmpty()[index]
        setEffect { HomeContract.Effect.Navigation.GoToBazaarDetails(bazaar) }
    }

    private fun handleProductNavigation(index: Int) {
        val sectionItem = currentState.categoryItems.orEmpty()[index]
        val category = Category(id = sectionItem.categoryId, title = sectionItem.title)
        setEffect { HomeContract.Effect.Navigation.GoToCategoryProductsList(category) }
    }

    private fun navigateToCategoryTab(showCategory: Boolean) {
        setEffect {
            HomeContract.Effect.Navigation.GoToCategoriesScreen(
                showCategory = showCategory
            )
        }
    }

    private fun handleAdClicked() {
        handleAdDismiss()
        val selectedItem = currentState.ads.orEmpty().firstOrNull() ?: return
        when {
            selectedItem.itemId != null -> {
                setEffect {
                    HomeContract.Effect.Navigation.GoToProductDetails(Product(id = selectedItem.itemId))
                }
            }

            selectedItem.brandId != null -> {
                setEffect {
                    HomeContract.Effect.Navigation.GoToBrandProductsList(Brand(id = selectedItem.brandId))
                }
            }

            selectedItem.categoryId != null -> {
                setEffect {
                    HomeContract.Effect.Navigation.GoToCategoryProductsList(Category(id = selectedItem.categoryId))
                }
            }
        }
    }

    private fun onProductClicked(index: Int, sectionIndex: Int) {
        val product = currentState.categoryItems?.get(sectionIndex)?.items?.get(index) ?: return
        setEffect { HomeContract.Effect.Navigation.GoToProductDetails(product) }
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
        when {
            selectedItem.itemId != null -> {
                setEffect {
                    HomeContract.Effect.Navigation.GoToProductDetails(Product(id = selectedItem.itemId))
                }
            }

            selectedItem.brandId != null -> {
                setEffect {
                    HomeContract.Effect.Navigation.GoToBrandProductsList(Brand(id = selectedItem.brandId))
                }
            }

            selectedItem.categoryId != null -> {
                setEffect {
                    HomeContract.Effect.Navigation.GoToCategoryProductsList(Category(id = selectedItem.categoryId))
                }
            }
        }
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
                is Result.Error -> setState { copy(showError = true) }
                is Result.Loading -> {}
                is Result.Success -> setState {
                    copy(
                        ads = it.data?.ads,
                        slides1 = it.data?.slider1,
                        slides2 = it.data?.slider2,
                        categoryItems = it.data?.categoryItems,
                        featuredBrands = it.data?.featuredBrands,
                        featuredCategories = it.data?.featuredCategories,
                        featuredBazzars = it.data?.featuredBzarz,
                    )
                }

                else -> {}
            }
        }
    })

}