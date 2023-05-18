package com.bazzar.android.presentation.productsList

import com.android.local.SharedPrefersManager
import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.request.SearchProductRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.R
import com.bazzar.android.common.orFalse
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.productDetail.ProductDetailContract
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
                setEffect { ProductContract.Effect.Navigation.GoToSearch }
            }

            is ProductContract.Event.OnProductClicked -> navigateToProductDetails(event.itemIndex)
            ProductContract.Event.ReachedListEnd -> loadMoreProducts()
            is ProductContract.Event.OnProductFavClicked -> handleProductFav(event.itemIndex)
        }
    }

    private fun handleProductFav(itemIndex: Int) = executeCatching({
        if (prefersManager.isUserLongedIn().not()) {
            setEffect { ProductContract.Effect.Navigation.GoToLogin }
            return@executeCatching
        }
        val list = currentState.productList?.toMutableList() ?: return@executeCatching
        val item = list[itemIndex]
        val isFav = item.isWishList.orFalse()
        if (isFav) {
            homeUseCase.addProductWishList(item.id.orZero())
                .collect { response ->
                    when (response) {
                        is Result.Success -> {
                            val updatedList = list.mapIndexed { index, product ->
                                if (index == itemIndex) {
                                    product.copy(isWishList = response.data.orFalse())
                                } else {
                                    product
                                }
                            }
                            setState {
                                copy(productList = updatedList)
                            }
                        }

                        else -> {}
                    }
                }
        } else {
            homeUseCase.deleteBazaarWishList(item.id.orZero())
                .collect { response ->
                    when (response) {
                        is Result.Success -> {
                            val updatedList = list.mapIndexed { index, product ->
                                if (index == itemIndex) {
                                    product.copy(isWishList = response.data.orFalse().not())
                                } else {
                                    product
                                }
                            }
                            setState {
                                copy(productList = updatedList)
                            }
                        }

                        else -> {}
                    }
                }
        }

    }, withLoading = false)

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
            currentState.searchRequest.copy(categoryId = selectedCategory.id, pageIndex = 0)

        // update the state
        setState { copy(subCategoryList = updatedCategoriesList, searchRequest = updatedRequest) }

        // load the new list
        loadProductData(updatedRequest)
    }

    fun init(brand: Brand?, category: Category?, searchTerm: String?) {

        if (isInitialized.not()) {
            var request = SearchProductRequest(categoryId = category?.id)

            // if the screen opened from categories get the sub-list and adding first one as all
            val subSubCategoriesList = category?.let { _category ->
                prefersManager.getCategoryList().orEmpty().filter { it.parentId == _category.id }
                    .toMutableList()
            }
            val updatedCategory =
                prefersManager.getCategoryList().orEmpty().firstOrNull { it.id == category?.id }
            if (subSubCategoriesList.isNullOrEmpty().not() && updatedCategory != null) {
                subSubCategoriesList?.add(
                    0,
                    updatedCategory.copy(
                        title = resourceProvider.getString(R.string.all),
                        isSelected = true
                    )
                )
            }

            // if screen opened form brands
            val updatedBrand =
                prefersManager.getBrandList().orEmpty().firstOrNull { it.id == brand?.id }
            updatedBrand?.id?.let {
                request = request.copy(brandList = listOf(it))
            }
            searchTerm?.let {
                request = request.copy(searchKey = searchTerm)
            }

            val title = when {
                (updatedCategory != null) -> updatedCategory.title.orEmpty()
                (updatedBrand != null) -> updatedBrand.title.orEmpty()
                (searchTerm.isNullOrEmpty().not()) -> searchTerm.orEmpty()
                else -> ""
            }
            setState {
                copy(
                    productScreenTitle = title,
                    searchRequest = request,
                    subCategoryList = subSubCategoriesList,
                    brand = updatedBrand,
                    category = updatedCategory
                )
            }
            loadProductData(request)
            isInitialized = true
        }
    }

    private fun loadMoreProducts() = executeCatching({
        val updatedRequest =
            currentState.searchRequest.copy(pageIndex = currentState.searchRequest.pageIndex + 1)
        setState { copy(isLoadingMore = true) }
        homeUseCase.getAllProductList(updatedRequest)
            .collect { productResponse ->
                when (productResponse) {
                    is Result.Success -> {
                        val updatedList = currentState.productList.orEmpty().toMutableList()
                        updatedList.addAll(productResponse.data.orEmpty())
                        setState {
                            copy(
                                productList = updatedList,
                                hasMore = productResponse.hasMoreData.orFalse(),
                                isLoadingMore = false,
                                searchRequest = updatedRequest
                            )
                        }
                    }

                    else -> {}
                }
            }
    })

    private fun loadProductData(request: SearchProductRequest) = executeCatching({
        homeUseCase.getAllProductList(request)
            .collect { productResponse ->
                when (productResponse) {
                    is Result.Error -> globalState.error(productResponse.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> setState {
                        copy(
                            productList = productResponse.data,
                            hasMore = productResponse.hasMoreData.orFalse(),
                            isLoadingMore = false,
                            showEmptyView = productResponse.data.orEmpty().isEmpty(),
                            searchRequest = request
                        )
                    }

                    else -> {}
                }
            }
    })
}

