package com.bazzar.android.presentation.bazarDetail

import com.android.local.SharedPrefersManager
import com.android.model.home.BazaarModel
import com.android.model.home.Brand
import com.android.model.home.Category
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
import com.bazzar.android.presentation.productDetail.ProductDetailContract
import com.bazzar.android.utils.IResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BazarDetailViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val resourceProvider: IResourceProvider,
    private val sharedPrefersManager: SharedPrefersManager,
) : BaseViewModel<BazarDetailContract.Event, BazarDetailContract.State, BazarDetailContract.Effect>(
    globalState
) {

    private var isInitialized = false
    override fun setInitialState() = BazarDetailContract.State()

    override fun handleEvents(event: BazarDetailContract.Event) {
        when (event) {
            is BazarDetailContract.Event.OnSubCategoryClicked -> onSubCategorySelected(event.categoryIndex)
            BazarDetailContract.Event.OnBackIconClicked -> BazarDetailContract.Effect.Navigation.GoToBack
            is BazarDetailContract.Event.OnProductClicked -> navigateToProductDetails(event.itemIndex)
            BazarDetailContract.Event.OnFavouriteClicked -> handleFavAction()
            BazarDetailContract.Event.OnShareCLicked -> shareLink()
            BazarDetailContract.Event.ReachedListEnd -> loadMoreProducts()
            is BazarDetailContract.Event.OnSliderClicked -> handleSliderAction(event.sliderItemIndex)
            is BazarDetailContract.Event.OnSearchTermChanged -> {}
            is BazarDetailContract.Event.OnProductFavClicked -> handleProductFav(event.itemIndex)
            is BazarDetailContract.Event.OnProductAddToCartClicked -> addProductToCart(event.itemIndex)
            BazarDetailContract.Event.OnContinueShoppingClicked -> setState {
                copy(showSuccessAddedToCart = false)
            }

            BazarDetailContract.Event.OnVisitYourCartClicked -> {
                setState {
                    copy(showSuccessAddedToCart = false)
                }
                setEffect { BazarDetailContract.Effect.Navigation.GoToCart }
            }
        }
    }

    private fun addProductToCart(itemIndex: Int) = executeCatching({
        if (sharedPrefersManager.isUserLongedIn().not()) {
            setEffect { BazarDetailContract.Effect.Navigation.GoToLogin }
            return@executeCatching
        }
        val itemDetail = currentState.productList?.get(itemIndex) ?: return@executeCatching

        homeUseCase.getAllProductDetails(itemDetail.id.orZero()).collect { response ->
            when (response) {
                is Result.Success -> {
                    val product = response.data!!
                    if (product.itemDetails.size > 1) {
                        setEffect { BazarDetailContract.Effect.Navigation.GoToProductDetails(product = product) }
                    } else if (product.itemDetails.size == 1) {
                        homeUseCase.addToCart(
                            AddToCartRequest(
                                itemDetailId = product.itemDetails.first().id.orZero(),
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
            setEffect { BazarDetailContract.Effect.Navigation.GoToLogin }
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
                    BazarDetailContract.Effect.Navigation.GoToProductDetails(Product(id = selectedItem.itemId))
                }
            }

            selectedItem.brandId != null -> {
                setEffect {
                    BazarDetailContract.Effect.Navigation.GoToBrandProductsList(Brand(id = selectedItem.brandId))
                }
            }

            selectedItem.categoryId != null -> {
                setEffect {
                    BazarDetailContract.Effect.Navigation.GoToCategoryProductsList(Category(id = selectedItem.categoryId))
                }
            }
        }
    }

    private fun shareLink() {
        setEffect {
            BazarDetailContract.Effect.OnShareBazaar(
                resourceProvider.getString(R.string.share_bazzar_text),
                currentState.shareLink.orEmpty()
            )
        }
    }

    private fun handleFavAction() = executeCatching({
        if (sharedPrefersManager.isUserLongedIn().not()) {
            setEffect { BazarDetailContract.Effect.Navigation.GoToLogin }
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
        setEffect { BazarDetailContract.Effect.Navigation.GoToProductDetails(item) }
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

    fun init(bazaar: BazaarModel) {

        if (isInitialized.not()) {
            val request = SearchProductRequest(marketerId = bazaar.id)
            setState {
                copy(
                    searchRequest = request, bazaar = bazaar
                )
            }
            getBazzarDetails(bazaar.id.orZero())
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
                    categoriesList.add(
                        0, Category(
                            null, resourceProvider.getString(R.string.all), isSelected = true
                        )
                    )
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
}

