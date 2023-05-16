package com.bazzar.android.presentation.categoryScreen

import com.android.local.SharedPrefersManager
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.categoryScreen.CategoryContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CategoriesViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val sharedPrefersManager: SharedPrefersManager
) : BaseViewModel<Event, State, Effect>(globalState) {


    private var isInitialized = false
    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.OnBrandItemClicked -> goToBrandCategory(event.brandItemIndex)
            is Event.OnCategoryItemClicked -> getSubCategories(event.categoryItemIndex)
            is Event.OnSearchClicked -> setEffect { Effect.Navigation.GoToSearch }
            is Event.OnSubCategoryItemClicked -> goToProductCategory(event.subCategoryItemIndex)
            is Event.OnToggleClicked -> setState { copy(showCategories = currentState.showCategories.not()) }
            is Event.OnDismissClicked -> handleDismissAction()
            is Event.OnSearchBrandChanged -> handleSearchBrand(event.term)
            is Event.OnSearchBrandClicked -> setState { copy(isSearchBrandOpen = true) }
            is Event.OnCancelSearchBrandClicked -> setState {
                copy(
                    isSearchBrandOpen = false,
                    searchBrandTerm = "",
                    brandListToShow = currentState.brandList?.toList()
                )
            }
        }
    }

    private fun handleDismissAction() {
        val updatedMainCategories =
            currentState.mainCategorisesList.orEmpty().mapIndexed { index, category ->
                category.copy(isSelected = false)
            }
        setState {
            copy(
                mainCategorisesList = updatedMainCategories,
                subCategoriesList = emptyList()
            )
        }
    }

    private fun getSubCategories(categoryItemIndex: Int) {
        // get the current selected category
        val updatedMainCategories =
            currentState.mainCategorisesList.orEmpty().mapIndexed { index, category ->
                category.copy(isSelected = index == categoryItemIndex)
            }
        val selectedCategory = updatedMainCategories[categoryItemIndex]

        // get the sub-category from the all list
        setState {
            copy(
                mainCategorisesList = updatedMainCategories,
                subCategoriesList = currentState.categoryList.orEmpty()
                    .filter { it.parentId == selectedCategory.id })
        }
    }

    private fun goToProductCategory(categoryItemIndex: Int) {
        // get the current selected category
        val selectedCategory = currentState.subCategoriesList?.get(categoryItemIndex) ?: return
        // navigate to product based on category
        setEffect {
            Effect.Navigation.GoToProductCategoryList(selectedCategory)
        }
    }

    private fun goToBrandCategory(brandItemIndex: Int) {
        // get the current selected category
        val selectedBrand = currentState.brandList?.get(brandItemIndex) ?: return
        // navigate to product based on category
        setEffect {
            Effect.Navigation.GoToProductBrandList(selectedBrand)
        }
    }

    fun init(showCategories: Boolean) {
        if (isInitialized.not()) {
            loadData(showCategories)

            isInitialized = true
        }
    }

    private fun loadData(showCategories: Boolean) = executeCatching({

        homeUseCase.getAllCategories().collect { categoriesResponse ->
            when (categoriesResponse) {
                is Result.Error -> globalState.error(categoriesResponse.message.orEmpty())
                is Result.Loading -> {}
                is Result.Success -> {
                    homeUseCase.getAllBrands().collect { brandsResponse ->
                        when (brandsResponse) {
                            is Result.Error -> globalState.error(brandsResponse.message.orEmpty())
                            is Result.Loading -> {}
                            is Result.Success -> {
                                val brandList = brandsResponse.data
                                sharedPrefersManager.saveCategoryList(categoriesResponse.data)
                                sharedPrefersManager.saveBrandList(brandList)
                                setState {
                                    copy(
                                        categoryList = categoriesResponse.data,
                                        mainCategorisesList = categoriesResponse.data.orEmpty()
                                            .filter { it.parentId == null },
                                        showCategories = showCategories,
                                        brandList = brandList,
                                        brandListToShow = brandList?.toList()
                                    )
                                }
                            }

                            else -> {}
                        }
                    }
                }

                else -> {}
            }
        }
    })

    private fun handleSearchBrand(term: String) = executeCatching({
        val brandListToShow =
            currentState.brandList?.filter { it.title?.contains(term, ignoreCase = true) == true }
        setState { copy(searchBrandTerm = term, brandListToShow = brandListToShow) }
    })
}
