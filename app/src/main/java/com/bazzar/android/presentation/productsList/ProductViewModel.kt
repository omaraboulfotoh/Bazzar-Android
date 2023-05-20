package com.bazzar.android.presentation.productsList

import com.android.local.SharedPrefersManager
import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.Filter
import com.android.model.request.AddToCartRequest
import com.android.model.request.SearchProductRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.R
import com.bazzar.android.common.orFalse
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.productsList.composables.filter.FilterType
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
    private var isSortFiltersLoaded = false
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
                loadProductData(currentState.searchRequest.copy(sorting = currentState.selectedSort?.sortKey))
            }

            is ProductContract.Event.OnSortItemSelected -> setState { copy(selectedSort = event.sort) }
            is ProductContract.Event.OnProductAddToCartClicked -> addProductToCart(event.itemIndex)
            ProductContract.Event.OnContinueShoppingClicked -> setState {
                copy(showSuccessAddedToCart = false)
            }

            ProductContract.Event.OnVisitYourCartClicked -> setEffect { ProductContract.Effect.Navigation.GoToCart }
            is ProductContract.Event.OnFilterTypeClicked -> handleOnFilterTypeClicked(event.filterType)
            is ProductContract.Event.OnSelectUnselectFilter ->
                handleOnSelectUnselectFilter(event.filter, event.isSelect)

            is ProductContract.Event.OnApplyFiltersClicked -> handleOnApplyFiltersClicked()
            is ProductContract.Event.OnResetFiltersClicked -> handleOnResetFiltersClicked()
            is ProductContract.Event.OnMaxPriceChanged -> setState { copy(selectedFilterMaxPrice = event.maxPrice) }
            is ProductContract.Event.OnMinPriceChanged -> setState { copy(selectedFilterMinPrice = event.minPrice) }
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
            homeUseCase.deleteProductWishList(item.id.orZero())
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

            // if it coming from search screen we haven't an id either for category id or brand id
            if (searchTerm.isNullOrEmpty()) {
                val sortFiltersQueryMap: Map<String, String> = when {
                    category?.id != null -> mapOf("CategoryId" to "${category.id}")
                    categoryId?.toIntOrNull() != null -> mapOf("CategoryId" to "${categoryId.toInt()}")
                    brand?.id != null -> mapOf("BrandId" to "${brand.id}")
                    else -> mapOf()
                }
                loadFiltersAndSorting(sortFiltersQueryMap)
            }
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
                    is Result.Success -> {

                        // if it coming from search screen we haven't an id either for category id or brand id
                        if (request.searchKey.isNullOrEmpty().not() && isSortFiltersLoaded.not()) {
                            productResponse.data?.firstOrNull()?.categoryId?.let {
                                loadFiltersAndSorting(mapOf("CategoryId" to "$it"))
                            }
                        }
                        setState {
                            copy(
                                productList = productResponse.data,
                                hasMore = productResponse.hasMoreData.orFalse(),
                                isLoadingMore = false,
                                showEmptyView = productResponse.data.orEmpty().isEmpty(),
                                searchRequest = request
                            )
                        }
                    }

                    else -> {}
                }
            }
    })

    private fun loadFiltersAndSorting(queryMap: Map<String, String>) = executeCatching({
        homeUseCase.loadFiltersAndSorting(queryMap)
            .collect { sortFilterResponse ->
                when (sortFilterResponse) {
                    is Result.Success -> setState {
                        isSortFiltersLoaded = true
                        copy(
                            sortFilter = sortFilterResponse.data,
                            categoryFilterList = sortFilterResponse.data?.categoryList?.toList(),
                            brandFilterList = sortFilterResponse.data?.brandList?.toList(),
                            colorFilterList = sortFilterResponse.data?.colorList?.toList(),
                            sizeFilterList = sortFilterResponse.data?.sizeList?.toList(),
                        )
                    }

                    else -> {}
                }
            }
    })

    private fun handleOnApplyFiltersClicked() {
        val categoryFilters = currentState.categoryFilterList
            ?.filter { it.isSelected }
            ?.map { it.id } ?: emptyList()

        val brandFilters = currentState.brandFilterList
            ?.filter { it.isSelected }
            ?.map { it.id } ?: emptyList()

        val colorFilters = currentState.colorFilterList
            ?.filter { it.isSelected }
            ?.map { it.id } ?: emptyList()

        val sizeFilters = currentState.sizeFilterList
            ?.filter { it.isSelected }
            ?.map { it.id } ?: emptyList()

        val maxPrice = currentState.selectedFilterMaxPrice ?: 0
        val minPrice = currentState.selectedFilterMinPrice ?: 0

        setState { copy(showFilterDialog = false) }
        loadProductData(
            currentState.searchRequest.copy(
                maxPrice = maxPrice,
                minPrice = minPrice,
                brandList = brandFilters,
                colorList = colorFilters,
                sizeList = sizeFilters,
            )
        )
    }

    private fun handleOnResetFiltersClicked() {
        setState {
            copy(
                selectedFilterType = null,
                filterListToShow = null,
                categoryFilterList = sortFilter?.categoryList,
                brandFilterList = sortFilter?.brandList,
                colorFilterList = sortFilter?.colorList,
                sizeFilterList = sortFilter?.sizeList,
                selectedFilterMaxPrice = null,
                selectedFilterMinPrice = null,
                numOfSelectedSizeFilters = 0,
                numOfSelectedColorFilters = 0,
                numOfSelectedBrandFilters = 0,
                numOfSelectedCategoryFilters = 0,
                numOfSelectedFilter = 0,
            )
        }
    }

    private fun handleOnFilterTypeClicked(filterType: FilterType) {
        when (filterType) {
            FilterType.FILTER_PRICE ->
                setState { copy(selectedFilterType = filterType, filterListToShow = null) }

            FilterType.FILTER_CATEGORY ->
                setState {
                    copy(
                        selectedFilterType = filterType,
                        filterListToShow = categoryFilterList
                    )
                }

            FilterType.FILTER_BRAND ->
                setState {
                    copy(
                        selectedFilterType = filterType,
                        filterListToShow = brandFilterList
                    )
                }

            FilterType.FILTER_COLOR ->
                setState {
                    copy(
                        selectedFilterType = filterType,
                        filterListToShow = colorFilterList
                    )
                }

            FilterType.FILTER_SIZE ->
                setState {
                    copy(
                        selectedFilterType = filterType,
                        filterListToShow = sizeFilterList
                    )
                }
        }
    }

    private fun handleOnSelectUnselectFilter(filter: Filter, isSelected: Boolean) {
        when (currentState.selectedFilterType) {
            FilterType.FILTER_CATEGORY -> {
                val filters = currentState.categoryFilterList
                filters?.find { it.id == filter.id }?.isSelected = isSelected
                val updated = filters?.toList()
                val numOfSelected = updated?.filter { it.isSelected }?.size ?: 0
                setState {
                    copy(
                        categoryFilterList = updated,
                        filterListToShow = updated,
                        numOfSelectedCategoryFilters = numOfSelected
                    )
                }
            }

            FilterType.FILTER_BRAND -> {
                val filters = currentState.brandFilterList
                filters?.find { it.id == filter.id }?.isSelected = isSelected
                val updated = filters?.toList()
                val numOfSelected = updated?.filter { it.isSelected }?.size ?: 0
                setState {
                    copy(
                        brandFilterList = updated,
                        filterListToShow = updated,
                        numOfSelectedBrandFilters = numOfSelected
                    )
                }
            }

            FilterType.FILTER_COLOR -> {
                val filters = currentState.colorFilterList
                filters?.find { it.id == filter.id }?.isSelected = isSelected
                val updated = filters?.toList()
                val numOfSelected = updated?.filter { it.isSelected }?.size ?: 0
                setState {
                    copy(
                        colorFilterList = updated,
                        filterListToShow = updated,
                        numOfSelectedColorFilters = numOfSelected
                    )
                }
            }

            FilterType.FILTER_SIZE -> {
                val filters = currentState.sizeFilterList
                filters?.find { it.id == filter.id }?.isSelected = isSelected
                val updated = filters?.toList()
                val numOfSelected = updated?.filter { it.isSelected }?.size ?: 0
                setState {
                    copy(
                        sizeFilterList = updated,
                        filterListToShow = updated,
                        numOfSelectedSizeFilters = numOfSelected
                    )
                }
            }

            else -> {}
        }
    }
}

