package com.bazzar.android.presentation.bazarDetail

import com.android.local.SharedPrefersManager
import com.android.model.home.BazaarModel
import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.Filter
import com.android.model.home.Product
import com.android.model.request.AddToCartRequest
import com.android.model.request.SearchProductRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.R
import com.bazzar.android.common.orFalse
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.bazarDetail.BazarDetailContract.*
import com.bazzar.android.presentation.productsList.ProductContract
import com.bazzar.android.presentation.productsList.composables.filter.FilterType
import com.bazzar.android.utils.IResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BazarDetailViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val resourceProvider: IResourceProvider,
    private val sharedPrefersManager: SharedPrefersManager,
) : BaseViewModel<Event, State, Effect>(
    globalState
) {

    private var isInitialized = false
    private var isSortFiltersLoaded = false
    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.OnSubCategoryClicked -> onSubCategorySelected(event.categoryIndex)
            Event.OnBackIconClicked -> Effect.Navigation.GoToBack
            is Event.OnProductClicked -> navigateToProductDetails(event.itemIndex)
            Event.OnFavouriteClicked -> handleFavAction()
            Event.OnShareCLicked -> shareLink()
            Event.ReachedListEnd -> loadMoreProducts()
            is Event.OnSliderClicked -> handleSliderAction(event.sliderItemIndex)
            is Event.OnSearchTermChanged -> {}
            is Event.OnProductFavClicked -> handleProductFav(event.itemIndex)
            is Event.OnProductAddToCartClicked -> addProductToCart(event.itemIndex)
            Event.OnContinueShoppingClicked -> setState {
                copy(showSuccessAddedToCart = false)
            }

            Event.OnVisitYourCartClicked -> {
                setState {
                    copy(showSuccessAddedToCart = false)
                }
                setEffect { Effect.Navigation.GoToCart }
            }

            is Event.OnApplyFiltersClicked -> handleOnApplyFiltersClicked()
            is Event.OnResetFiltersClicked -> handleOnResetFiltersClicked()
            is Event.OnMaxPriceChanged -> setState { copy(selectedFilterMaxPrice = event.maxPrice) }
            is Event.OnMinPriceChanged -> setState { copy(selectedFilterMinPrice = event.minPrice) }
            is Event.OnDismissFilterDialogClicked -> {
                setState { copy(showFilterDialog = false) }
            }

            is Event.OnDismissSortDialogClicked -> {
                setState { copy(showSortDialog = false) }
            }

            is Event.OnFilterClicked -> {
                setState { copy(showFilterDialog = true) }
            }

            is Event.OnSortClicked -> {
                setState { copy(showSortDialog = true) }
            }

            is Event.OnApplySortClicked -> {
                setState { copy(showSortDialog = false) }
                loadProductData(
                    currentState.searchRequest.copy(
                        sorting = currentState.selectedSort?.sortKey,
                        pageIndex = 0
                    )
                )
            }

            is Event.OnSortItemSelected -> setState { copy(selectedSort = event.sort) }
            Event.OnVisitYourCartClicked -> {
                setState {
                    copy(showSuccessAddedToCart = false)
                }
                setEffect { Effect.Navigation.GoToCart }
            }

            is Event.OnFilterTypeClicked -> handleOnFilterTypeClicked(event.filterType)
            is Event.OnSelectUnselectFilter -> handleOnSelectUnselectFilter(
                event.filter,
                event.isSelect
            )
        }
    }

    private fun addProductToCart(itemIndex: Int) = executeCatching({
        if (sharedPrefersManager.isUserLongedIn().not()) {
            setEffect { Effect.Navigation.GoToLogin }
            return@executeCatching
        }
        val itemDetail = currentState.productList?.get(itemIndex) ?: return@executeCatching

        homeUseCase.getAllProductDetails(itemDetail.id.orZero()).collect { response ->
            when (response) {
                is Result.Success -> {
                    val product = response.data!!
                    if (product.itemDetails?.size.orZero() > 1) {
                        setEffect { Effect.Navigation.GoToProductDetails(product = product) }
                    } else if (product.itemDetails?.size.orZero() == 1) {
                        homeUseCase.addToCart(
                            AddToCartRequest(
                                itemDetailId = product.itemDetails?.first()?.id.orZero(),
                                marketerId = currentState.bazaar?.id
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
        if (sharedPrefersManager.isUserLongedIn().not()) {
            setEffect { Effect.Navigation.GoToLogin }
            return@executeCatching
        }
        val list = currentState.productList?.toMutableList() ?: return@executeCatching
        val item = list[itemIndex]
        val isFav = item.isWishList.orFalse().not()
        if (isFav.not()) {
            homeUseCase.addProductWishList(item.id.orZero()).collect { response ->
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
            homeUseCase.deleteProductWishList(item.id.orZero()).collect { response ->
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

    private fun handleSliderAction(sliderItemIndex: Int) {
        val sliderList = currentState.slider
        val selectedItem = sliderList[sliderItemIndex]
        when {
            selectedItem.itemId != null -> {
                setEffect {
                    Effect.Navigation.GoToProductDetails(Product(id = selectedItem.itemId))
                }
            }

            selectedItem.brandId != null -> {
                setEffect {
                    Effect.Navigation.GoToBrandProductsList(Brand(id = selectedItem.brandId))
                }
            }

            selectedItem.categoryId != null -> {
                setEffect {
                    Effect.Navigation.GoToCategoryProductsList(Category(id = selectedItem.categoryId))
                }
            }
        }
    }

    private fun shareLink() {
        setEffect {
            Effect.OnShareBazaar(
                resourceProvider.getString(R.string.share_bazzar_text),
                currentState.shareLink.orEmpty()
            )
        }
    }

    private fun handleFavAction() = executeCatching({
        if (sharedPrefersManager.isUserLongedIn().not()) {
            setEffect { Effect.Navigation.GoToLogin }
            return@executeCatching
        }

        val fav = currentState.isFavourite.not()
        val bazaarId = currentState.bazaar?.id ?: return@executeCatching
        if (fav) {
            homeUseCase.addBazaarWishList(bazaarId).collect { response ->
                when (response) {
                    is Result.Success -> setState {
                        copy(isFavourite = response.data.orFalse())
                    }

                    else -> {}
                }
            }
        } else {
            homeUseCase.deleteBazaarWishList(bazaarId).collect { response ->
                when (response) {
                    is Result.Success -> setState {
                        copy(isFavourite = response.data.orFalse().not())
                    }

                    else -> {}
                }
            }
        }
    }, withLoading = false)


    private fun navigateToProductDetails(itemIndex: Int) {
        val item = currentState.productList?.get(itemIndex) ?: return
        // navigate to details
        setEffect { Effect.Navigation.GoToProductDetails(item) }
    }

    private fun onSubCategorySelected(subSubCategoryIndex: Int) {
        //flip state
        val selectedCategory = currentState.categoriesList[subSubCategoryIndex]

        // update the current list
        val updatedCategoriesList = currentState.categoriesList.mapIndexed { index, category ->
            category.copy(isSelected = index == subSubCategoryIndex)
        }
        // update the search request
        val updatedRequest =
            currentState.searchRequest.copy(categoryId = selectedCategory.id, pageIndex = 0)

        // update the state
        setState { copy(categoriesList = updatedCategoriesList, searchRequest = updatedRequest) }

        // load the new list
        loadProductData(updatedRequest)
    }

    fun init(bazaar: BazaarModel?, marketerId: String?) {

        if (isInitialized.not()) {
            val request = SearchProductRequest(marketerId = bazaar?.id ?: marketerId?.toIntOrNull())
            setState {
                copy(
                    searchRequest = request, bazaar = bazaar
                )
            }
            getBazzarDetails((bazaar?.id ?: marketerId?.toIntOrNull()).orZero())
            loadProductData(request)
            isInitialized = true
        }
    }

    private fun getBazzarDetails(bazaarId: Int) = executeCatching({
        homeUseCase.getBazaarDetails(bazaarId).collect { response ->
            when (response) {
                is Result.Error -> globalState.error(response.message.orEmpty())
                is Result.Success -> setState {
                    val data = response.data
                    val categoriesList = data?.categoryList.orEmpty().toMutableList()
                    if (categoriesList.isNotEmpty())
                        categoriesList[0] = categoriesList[0].copy(isSelected = true)
                    val sortFiltersQueryMap: Map<String, String> = when {
                        (categoriesList.isNotEmpty()) -> mapOf("CategoryId" to "${categoriesList.first { it.isSelected }.id}")
                        else -> mapOf()
                    }
                    loadFiltersAndSorting(sortFiltersQueryMap)
                    copy(
                        isFavourite = data?.isWishList.orFalse(),
                        categoriesList = categoriesList,
                        slider = data?.slider.orEmpty(),
                        shareLink = data?.shareURL.orEmpty()
                    )
                }

                else -> {}
            }
        }
    })

    private fun loadMoreProducts() = executeCatching({
        val updatedRequest =
            currentState.searchRequest.copy(pageIndex = currentState.searchRequest.pageIndex + 1)
        setState { copy(isLoadingMore = true) }
        homeUseCase.getAllProductList(updatedRequest).collect { productResponse ->
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
        homeUseCase.getAllProductList(request).collect { productResponse ->
            when (productResponse) {
                is Result.Error -> globalState.error(productResponse.message.orEmpty())
                is Result.Loading -> globalState.loading(true)
                is Result.Success -> setState {
                    copy(
                        productList = productResponse.data,
                        hasMore = productResponse.hasMoreData.orFalse(),
                        isLoadingMore = false,
                        searchRequest = request
                    )
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
                pageIndex = 0,
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

