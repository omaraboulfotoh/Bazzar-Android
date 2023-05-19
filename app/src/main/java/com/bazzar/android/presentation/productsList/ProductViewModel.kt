package com.bazzar.android.presentation.productsList

import com.android.local.SharedPrefersManager
import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.request.AddToCartRequest
import com.android.model.request.SearchProductRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.R
import com.bazzar.android.common.orFalse
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.bazarDetail.BazarDetailContract
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
            is ProductContract.Event.OnBackIconClicked -> setEffect { ProductContract.Effect.Navigation.GoToBack }
            is ProductContract.Event.OnSearchClicked -> {
                setEffect { ProductContract.Effect.Navigation.GoToSearch }
            }

            is ProductContract.Event.OnProductClicked -> navigateToProductDetails(event.itemIndex)
            ProductContract.Event.ReachedListEnd -> loadMoreProducts()
            is ProductContract.Event.OnProductFavClicked -> handleProductFav(event.itemIndex)
            is ProductContract.Event.OnDismissFilterDialogClicked -> {
                setState { copy(showFilterDialog = false) }
            }

            is ProductContract.Event.OnDismissSortDialogClicked -> {
                setState { copy(showSortDialog = false) }
            }

            is ProductContract.Event.OnFilterClicked -> {
                setState { copy(showFilterDialog = true) }
            }

            is ProductContract.Event.OnSortClicked -> {
                setState { copy(showSortDialog = true) }
            }

            is ProductContract.Event.OnApplySortClicked -> {
                setState { copy(showSortDialog = false) }
                loadProductData(currentState.searchRequest.copy(sorting = currentState.selectedSortItem?.sortKey))
            }

            is ProductContract.Event.OnSortItemSelected -> setState { copy(selectedSortItem = event.sortItem) }
            is ProductContract.Event.OnProductAddToCartClicked -> addProductToCart(event.itemIndex)
        }
    }

    private fun addProductToCart(itemIndex: Int) = executeCatching({
        if (prefersManager.isUserLongedIn().not()) {
            setEffect { ProductContract.Effect.Navigation.GoToLogin }
            return@executeCatching
        }
        val itemDetail = currentState.productList?.get(itemIndex) ?: return@executeCatching

        homeUseCase.getAllProductDetails(itemDetail.id.orZero()).collect { response ->
            when (response) {
                is Result.Success -> {
                    val product = response.data!!
                    if (product.itemDetails.size > 1) {
                        setEffect { ProductContract.Effect.Navigation.GoToProductDetailPage(product = product) }
                    } else if (product.itemDetails.size == 1) {
                        homeUseCase.addToCart(
                            AddToCartRequest(
                                itemDetailId = product.itemDetails.first().id.orZero()
                            )
                        ).collect { response ->
                            when (response) {
                                is Result.Error -> globalState.error(response.message.orEmpty())
                                is Result.Success -> {
                                    setState { copy(showSuccessAddedToCart = true) }
                                }

                                else -> {}
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    })


    private fun handleProductFav(itemIndex: Int) = executeCatching({
        if (prefersManager.isUserLongedIn().not()) {
            setEffect { ProductContract.Effect.Navigation.GoToLogin }
            return@executeCatching
        }
        val list = currentState.productList?.toMutableList() ?: return@executeCatching
        val item = list[itemIndex]
        val isFav = item.isWishList.orFalse().not()
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

    fun init(
        brand: Brand?,
        category: Category?,
        searchTerm: String?,
        brandId: String?,
        categoryId: String?
    ) {

        if (isInitialized.not()) {
            var request =
                SearchProductRequest(categoryId = category?.id ?: categoryId?.toIntOrNull())

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

            val sortFiltersQueryMap: Map<String, String> = when {
                category?.id != null -> mapOf("CategoryId" to "${category.id}")
                categoryId?.toIntOrNull() != null -> mapOf("CategoryId" to "${categoryId.toInt()}")
                brand?.id != null -> mapOf("BrandId" to "${brand.id}")
                else -> mapOf()
            }
            loadFiltersAndSorting(sortFiltersQueryMap, searchTerm)
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

    private fun loadFiltersAndSorting(
        queryMap: Map<String, String>,
        searchTerm: String? = null
    ) = executeCatching({
        // if it coming from search screen we haven't an id either for category id or brand id
        if (searchTerm.isNullOrEmpty().not()) {
            homeUseCase.getAllProductList(SearchProductRequest(searchKey = searchTerm))
                .collect { productResponse ->
                    when (productResponse) {
                        is Result.Success -> {
                            productResponse.data?.firstOrNull()?.categoryId?.let {
                                val searchTermQueryMap = mapOf("CategoryId" to "$it")
                                homeUseCase.loadFiltersAndSorting(searchTermQueryMap)
                                    .collect { sortFilterResponse ->
                                        when (sortFilterResponse) {
                                            is Result.Success -> setState { copy(sortFilter = sortFilterResponse.data) }
                                            else -> {}
                                        }
                                    }
                            }
                        }

                        else -> {}
                    }
                }
        } else {
            homeUseCase.loadFiltersAndSorting(queryMap)
                .collect { sortFilterResponse ->
                    when (sortFilterResponse) {
                        is Result.Success -> setState { copy(sortFilter = sortFilterResponse.data) }
                        else -> {}
                    }
                }
        }
    })
}

