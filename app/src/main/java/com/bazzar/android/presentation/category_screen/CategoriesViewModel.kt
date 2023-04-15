package com.bazzar.android.presentation.category_screen

import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.category_screen.CategoryContract.Effect
import com.bazzar.android.presentation.category_screen.CategoryContract.Event
import com.bazzar.android.presentation.category_screen.CategoryContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CategoriesViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
) : BaseViewModel<Event, State, Effect>(globalState) {


    private var isInitialized = false
    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.OnBrandItemClicked -> goToBrandCategory(event.brandItemIndex)
            is Event.OnCategoryItemClicked -> getSubCategories(event.categoryItemIndex)
            Event.OnSearchClicked -> {}
            is Event.OnSubCategoryItemClicked -> goToProductCategory(event.subCategoryItemIndex)
            Event.OnToggleClicked -> setState { copy(showCategories = currentState.showCategories.not()) }
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
                subCategoriesList = currentState.subCategoriesList.orEmpty()
                    .filter { it.parentId == selectedCategory.id })
        }
    }

    private fun goToProductCategory(categoryItemIndex: Int) {
        // get the current selected category
        val selectedCategory = currentState.subCategoriesList?.get(categoryItemIndex) ?: return
        // navigate to product based on category
        setEffect {
            CategoryContract.Effect.Navigation.GoToProductCategoryList(selectedCategory)
        }
    }

    private fun goToBrandCategory(brandItemIndex: Int) {
        // get the current selected category
        val selectedBrand = currentState.brandList?.get(brandItemIndex) ?: return
        // navigate to product based on category
        setEffect {
            CategoryContract.Effect.Navigation.GoToProductBrandList(selectedBrand)
        }
    }

    fun init() {
        if (isInitialized.not()) {
            loadData()

            isInitialized = true
        }
    }

    private fun loadData() = executeCatching({

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
                                setState {
                                    copy(
                                        categoryList = categoriesResponse.data,
                                        mainCategorisesList = categoriesResponse.data.orEmpty()
                                            .filter { it.parentId == null },
                                        brandList = brandsResponse.data
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
}
