package com.bazzar.android.presentation.productsList

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
            ProductContract.Event.OnBackIconClicked -> setEffect { ProductContract.Effect.Navigation.GoToBack }
            ProductContract.Event.OnSearchClicked -> {
                setState { copy(isSearchClicked = isSearchClicked.not()) }
            }

            is ProductContract.Event.OnProductClicked -> navigateToProductDetails(event.itemIndex)
        }
    }

    private fun navigateToProductDetails(itemIndex: Int) {
        val item = currentState.productList?.get(itemIndex) ?: return
        // navigate to details
        setEffect { ProductContract.Effect.Navigation.GoToProductDetailPage(item) }
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

    fun init(brand: Brand?, category: Category?, searchTerm: String?) {

        if (isInitialized.not()) {
            if (searchTerm.isNullOrEmpty().not()) {
                val searchTermRequest = SearchProductRequest(searchKey = searchTerm)
                setState {
                    copy(
                        productScreenTitle = searchTerm!!,
                        searchRequest = searchTermRequest,
                    )
                }
                loadProductData(searchTermRequest)
                return
            }
            var request = SearchProductRequest(categoryId = category?.id)

            // if the screen opened from categories get the sub-list and adding first one as all
            val subSubCategoriesList = category?.let { _category ->
                prefersManager.getCategoryList().orEmpty().filter { it.parentId == _category.id }
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

