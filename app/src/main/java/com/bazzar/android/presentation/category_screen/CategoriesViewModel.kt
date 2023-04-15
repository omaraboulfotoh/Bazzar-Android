package com.bazzar.android.presentation.category_screen

import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.category_screen.CategoryContract.*
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
            is Event.OnBrandItemClicked -> {}
            is Event.OnCategoryItemClicked -> getSubCategories(event.categoryItemIndex)
            Event.OnSearchClicked -> {}
            is Event.OnSubCategoryItemClicked -> {}
            Event.OnToggleClicked -> {}
        }
    }

    private fun getSubCategories(categoryItemIndex: Int) {
        // get the current selected category
        val selectedCategory = currentState.mainCategorisesList?.get(categoryItemIndex) ?: return

        // get the sub-category from the all list
        setState {
            copy(
                subCategoriesList = currentState.categoryList.orEmpty()
                    .filter { it.parentId == selectedCategory.id })
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
