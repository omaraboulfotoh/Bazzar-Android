package com.bazzar.android.presentation.bazarDetail

import com.android.local.SharedPrefersManager
import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.request.SearchProductRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.R
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.utils.IResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BazarDetailViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val prefersManager: SharedPrefersManager,
    private val resourceProvider: IResourceProvider,
) :
    BaseViewModel<BazarDetailContract.Event, BazarDetailContract.State, BazarDetailContract.Effect>(
        globalState
    ) {

    private var isInitialized = false
    override fun setInitialState() = BazarDetailContract.State()

    override fun handleEvents(event: BazarDetailContract.Event) {
        when (event) {
            is BazarDetailContract.Event.OnSubCategoryClicked -> onSubCategorySelected(event.categoryIndex)
            BazarDetailContract.Event.OnBackIconClicked -> BazarDetailContract.Effect.Navigation.GoToBack
            BazarDetailContract.Event.OnSearchClicked -> {
                setState { copy(isSearchClicked = isSearchClicked.not()) }
            }

            is BazarDetailContract.Event.OnProductClicked -> navigateToProductDetails(event.itemIndex)
        }
    }

    private fun navigateToProductDetails(itemIndex: Int) {
        val item = currentState.productList?.get(itemIndex) ?: return
        // navigate to details
        setEffect { BazarDetailContract.Effect.Navigation.GoToProductDetailPage(item) }
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
            currentState.searchRequest.copy(marketerId = selectedCategory.id, pageIndex = 1)

        // update the state
        setState { copy(subCategoryList = updatedCategoriesList, searchRequest = updatedRequest) }

        // load the new list
        loadProductData(updatedRequest)
    }

    fun init(brand: Brand?, category: Category?) {

        if (isInitialized.not()) {
            var request = SearchProductRequest(marketerId = category?.id)

            // if the screen opened from categories get the sub-list and adding first one as all
            val subSubCategoriesList = category?.let { category ->
                prefersManager.getCategoryList().orEmpty().filter { it.parentId == category.id }
                    .toMutableList()
            }
            if (subSubCategoriesList.isNullOrEmpty().not() && category != null) {
                subSubCategoriesList?.add(
                    0,
                    category.copy(
                        title = resourceProvider.getString(R.string.all),
                        isSelected = true
                    )
                )
            }
            val title = when {
                (category != null) -> category.title.orEmpty()
                (brand != null) -> brand.title.orEmpty()
                else -> ""
            }
            // if screen opened form brands
            brand?.id?.let {
                request = request.copy(brandList = listOf(it))
            }
            setState {
                copy(
                    productScreenTitle = title,
                    searchRequest = request,
                    subCategoryList = subSubCategoriesList,
                    brand = brand,
                    category = category
                )
            }
            loadProductData(request)
            isInitialized = true
        }
    }

    private fun loadProductData(request: SearchProductRequest) = executeCatching({
        homeUseCase.getAllProductList(request)
            .collect { productResponse ->
                when (productResponse) {
                    is Result.Error -> globalState.error(productResponse.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> setState {
                        copy(productList = productResponse.data)
                    }

                    else -> {}
                }
            }
    })
}

