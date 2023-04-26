package com.bazzar.android.presentation.productList

import com.android.local.SharedPrefersManager
import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.SearchProductRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.R
import com.bazzar.android.di.CommonModule_ResourceProviderFactory
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.utils.IResourceProvider
import com.bazzar.android.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val prefersManager: SharedPrefersManager,
    private val resourceProvider: IResourceProvider,
) :
    BaseViewModel<ProductContract.Event, ProductContract.State, ProductContract.Effect>(
        globalState
    ) {

    private var isInitialized = false
    override fun setInitialState() = ProductContract.State()

    override fun handleEvents(event: ProductContract.Event) {
        when (event) {
            is ProductContract.Event.OnSubCategoryClicked -> onSubCategorySelected(event.categoryIndex)
            ProductContract.Event.OnBackIconClicked -> ProductContract.Effect.Navigation.GoToBack
            ProductContract.Event.OnSearchClicked -> {
                setState { copy(isSearchClicked = isSearchClicked.not()) }
            }
        }
    }

    private fun onSubCategorySelected(subSubCategoryIndex: Int) {
        //flip state
        val selectedCategory = currentState.subCategoryList?.get(subSubCategoryIndex) ?: return

        // update the current list
        val updatedCategoriesList = currentState.subCategoryList?.mapIndexed { index, category ->
            category.copy(isSelected = index == subSubCategoryIndex)
        }
        // update the search request
        val updatedRequest =
            currentState.searchRequest.copy(categoryId = selectedCategory.id, pageIndex = 1)

        // update the state
        setState { copy(subCategoryList = updatedCategoriesList, searchRequest = updatedRequest) }

        // load the new list
        loadProductData(updatedRequest)
    }

    fun init(brand: Brand?, category: Category?) {

        if (isInitialized.not()) {
            var request = SearchProductRequest(categoryId = category?.id)

            // if the screen opened from categories get the sub-list and adding first one as all
            val subSubCategoriesList = category?.let {
                prefersManager.getCategoryList().orEmpty().filter { it.parentId == it.id }
                    .toMutableList()
            }
            if (subSubCategoriesList.isNullOrEmpty().not() && category != null) {
                subSubCategoriesList?.add(
                    0,
                    category.copy(title = resourceProvider.getString(R.string.all))
                )
            }

                // if screen opened form brands
            brand?.id?.let {
                request = request.copy(brandList = listOf(it))
            }
            setState { copy(searchRequest = request, subCategoryList = subSubCategoriesList) }
            loadProductData(request)
            isInitialized = true
        }
    }

    private fun loadProductData(request: SearchProductRequest) = executeCatching({
        homeUseCase.getAllProductList(request)
            .collect { productResponse ->
                when (productResponse) {
                    is Result.Error -> globalState.error(productResponse.message.orEmpty())
                    is Result.Loading -> {}
                    is Result.Success -> setState {
                        copy(
                            productList = productResponse.data
                        )
                    }

                    else -> {}
                }
            }
    })
}

