package com.bazzar.android.presentation.productDetail

import com.android.local.SharedPrefersManager
import com.android.model.home.BazaarModel
import com.android.model.home.Brand
import com.android.model.home.ItemDetail
import com.android.model.home.ItemImages
import com.android.model.home.Product
import com.android.model.request.AddToCartRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.R
import com.bazzar.android.common.orFalse
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.main.MainContract
import com.bazzar.android.presentation.productDetail.ProductDetailContract.Effect
import com.bazzar.android.presentation.productDetail.ProductDetailContract.Event
import com.bazzar.android.presentation.productDetail.ProductDetailContract.State
import com.bazzar.android.utils.IResourceProvider
import com.bazzar.android.utils.remoteconfig.RemoteConfiguration
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val sharedPrefersManager: SharedPrefersManager,
    private val resourceProvider: IResourceProvider,
    private val remoteConfiguration: RemoteConfiguration
) : BaseViewModel<Event, State, Effect>(
    globalState
) {

    private var isInitialized = false
    override fun setInitialState() = State()


    override fun handleEvents(event: Event) {
        when (event) {
            // navigation
            is Event.OnBackIconClicked -> setEffect { Effect.Navigation.GoToBack }
            is Event.OnShareClicked -> shareProduct()
            is Event.OnVisitYourCartClicked -> {
                setState { copy(showSuccessAddedToCart = false) }
                publishMainEventBut(MainContract.CART_TAB)
            }

            is Event.OnSeeMoreBrandClicked -> navigateToBrandItems()
            is Event.OnRelatedItemClicked -> openSelectedProduct(event.itemIndex)
            is Event.OnBuyNowClicked -> addToCart()
            is Event.OnContinueShoppingClicked -> setState {
                copy(
                    showSuccessAddedToCart = false
                )
            }

            is Event.OnTackToUsClicked -> setEffect {
                Effect.Navigation.GoToTalkToUs(
                    remoteConfiguration.getString("WHATSAPP_NUMBER")
                )
            }

            is Event.OnImageClicked -> handleOnImageClicked(event.index)
            // state
            is Event.OnColorItemSelected -> updateColor(event.colorIndex)
            is Event.OnSizeItemSelected -> updateSizeAndItemId(event.sizeIndex)
            is Event.OnSeeMoreClicked -> setState { copy(isTextExpanded = isTextExpanded.not()) }
            is Event.OnRelatedItemFavClicked -> handleItemFav(event.itemIndex)
            Event.OnFavClicked -> handleProductFav()
            is Event.OnProductAddToCartClicked -> addProductToCart(event.itemIndex)
        }
    }


    private fun addProductToCart(itemIndex: Int) = executeCatching({
        if (sharedPrefersManager.isUserLongedIn().not()) {
            setEffect { Effect.Navigation.GoToLogin }
            return@executeCatching
        }
        val itemDetail =
            currentState.productDetail?.relatedItems?.get(itemIndex) ?: return@executeCatching

        homeUseCase.getAllProductDetails(itemDetail.id.orZero()).collect { response ->
            when (response) {
                is Result.Success -> {
                    val product = response.data!!
                    if (product.itemDetails?.size.orZero() > 1) {
                        setEffect { Effect.Navigation.GoToOpenProduct(product = product) }
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

    private fun handleProductFav() = executeCatching({
        if (sharedPrefersManager.isUserLongedIn().not()) {
            setEffect { Effect.Navigation.GoToLogin }
            return@executeCatching
        }
        val fav = currentState.isFavourite.not()
        val itemId = currentState.productDetail?.id ?: return@executeCatching
        if (fav) {
            homeUseCase.addProductWishList(itemId)
                .collect { response ->
                    when (response) {
                        is Result.Success -> setState {
                            copy(isFavourite = response.data.orFalse())
                        }

                        else -> {}
                    }
                }
        } else {
            homeUseCase.deleteProductWishList(itemId)
                .collect { response ->
                    when (response) {
                        is Result.Success -> setState {
                            copy(isFavourite = response.data.orFalse().not())
                        }

                        else -> {}
                    }
                }
        }
    }, withLoading = false)


    private fun handleItemFav(itemIndex: Int) = executeCatching({
        if (sharedPrefersManager.isUserLongedIn().not()) {
            setEffect { Effect.Navigation.GoToLogin }
            return@executeCatching
        }
        val list =
            currentState.productDetail?.relatedItems?.toMutableList() ?: return@executeCatching
        val item = list[itemIndex]
        val isFav = item.isWishList.orFalse().not()
        if (isFav) {
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
                            copy(productDetail = currentState.productDetail?.copy(relatedItems = updatedList))
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
                            copy(productDetail = currentState.productDetail?.copy(relatedItems = updatedList))
                        }
                    }

                    else -> {}
                }
            }
        }

    }, withLoading = false)

    private fun openSelectedProduct(itemIndex: Int) {
        val product = currentState.productDetail?.relatedItems.orEmpty()[itemIndex]
        setEffect { Effect.Navigation.GoToOpenProduct(product) }
    }

    private fun handleOnImageClicked(index: Int) {
        val imagePaths = currentState.selectedColoredImagesList.toMutableList()
        val clickedImage = imagePaths[index]
        imagePaths.removeAt(index)
        imagePaths.add(0, clickedImage)
        setEffect {
            Effect.Navigation.GoToImageViewer(
                imagePathList = imagePaths, product = currentState.productDetail
            )
        }
    }

    private fun addToCart() = executeCatching({
        if (sharedPrefersManager.isUserLongedIn().not()) {
            setEffect { Effect.Navigation.GoToLogin }
            return@executeCatching
        }
        val itemDetail = currentState.productDetail?.selectedItemDetails ?: return@executeCatching
        homeUseCase.addToCart(
            AddToCartRequest(
                itemDetailId = itemDetail.id.orZero(),
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
    })

    private fun navigateToBrandItems() {
        val brand = Brand(
            id = currentState.productDetail?.brandId,
            title = currentState.productDetail?.brandTitle,
            imagePath = currentState.productDetail?.brandImagePath,
        )

        // navigate to brands products list
        setEffect { Effect.Navigation.GoToProductBrandList(brand) }
    }

    private fun shareProduct() {
        val product = currentState.productDetail ?: return
        // share product
        setEffect {
            Effect.ShareProduct(
                shareText = resourceProvider.getString(R.string.share_product_text),
                shareLink = product.shareURL.orEmpty()
            )
        }
    }

    private fun updateSizeAndItemId(sizeIndex: Int) {
        val selectedItemDetail = currentState.sizeTitleList[sizeIndex]
        setState {
            copy(
                selectedItemDetail = selectedItemDetail,
                productDetail = currentState.productDetail?.copy(
                    selectedItemDetails = selectedItemDetail.copy(
                        quantity = 1
                    )
                )
            )
        }
    }

    private fun updateColor(colorIndex: Int) {
        val selectedColor = currentState.colorsList[colorIndex]
        val updatedSizedList =
            filterSizeWithColorIdsList(selectedColor.colorId.orZero(), currentState.productDetail)
        setState {
            copy(
                sizeTitleList = updatedSizedList,
                selectedItemDetail = updatedSizedList.firstOrNull(),
                productDetail = currentState.productDetail?.copy(
                    selectedItemDetails = selectedItemDetail?.copy(
                        quantity = 1
                    )
                )
            )
        }
    }

    fun init(product: Product?, itemId: String?, bazaar: BazaarModel?) {
        if (isInitialized.not()) {
            loadProductData(product?.getProductId() ?: itemId?.toIntOrNull() ?: 0, bazaar)
            isInitialized = true
        }
    }

    private fun loadProductData(productId: Int, bazaar: BazaarModel?) = executeCatching({
        homeUseCase.getAllProductDetails(productId).collect { productDetailResponse ->
            when (productDetailResponse) {
                is Result.Error -> globalState.error(productDetailResponse.message.orEmpty())
                is Result.Loading -> {}
                is Result.Success -> {
                    val productDetail = productDetailResponse.data
                    val selectedItemDetail = productDetail?.itemDetails?.first()?.copy(quantity = 1)
                    val colorsIdsList =
                        productDetail?.itemDetails.orEmpty().map { it.colorId }.toSet().toList()
                    val colorsList = getColorsList(colorsIdsList, productDetail)
                    val selectedImagedList =
                        filterColorImagesWithId(selectedItemDetail?.colorId.orZero(), productDetail)
                    val selectedTitleList = filterSizeWithColorIdsList(
                        selectedItemDetail?.colorId.orZero(), productDetail
                    )

                    setState {
                        copy(
                            productDetail = productDetail?.copy(selectedItemDetails = selectedItemDetail),
                            selectedItemDetail = selectedItemDetail,
                            selectedColoredImagesList = selectedImagedList,
                            sizeTitleList = selectedTitleList,
                            colorsList = colorsList,
                            bazaar = bazaar,
                            isFavourite = productDetail?.isWishList.orFalse()
                        )
                    }
                }

                else -> {}
            }
        }
    })

    private fun getColorsList(
        colorsIdsList: List<Int?>, productDetail: Product?
    ): MutableList<ItemImages> {
        val list = mutableListOf<ItemImages>()
        productDetail?.let {
            colorsIdsList.forEach { colorId ->
                list.add(productDetail.itemImages?.firstOrNull { it.colorId == colorId }
                    ?: ItemImages(
                        colorId,
                        colorTitle = productDetail.itemDetails?.first { it.colorId == colorId }?.colorTitle
                    )
                )
            }
        }
        return list
    }

    private fun filterColorImagesWithId(colorId: Int, productDetail: Product?) =
        productDetail?.itemImages?.filter { it.colorId == colorId }.orEmpty()
            .mapNotNull { it.imagePath }

    private fun filterSizeWithColorIdsList(
        colorId: Int, productDetail: Product?
    ): List<ItemDetail> = productDetail?.itemDetails?.filter { it.colorId == colorId }.orEmpty()
}


