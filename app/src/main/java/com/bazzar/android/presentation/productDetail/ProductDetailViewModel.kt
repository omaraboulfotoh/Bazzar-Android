package com.bazzar.android.presentation.productDetail

import com.android.local.SharedPrefersManager
import com.android.model.home.Brand
import com.android.model.home.ItemDetail
import com.android.model.home.ItemImages
import com.android.model.home.Product
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.R
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.app.ConfirmationDialogParams
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.utils.IResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val sharedPrefersManager: SharedPrefersManager,
    private val resourceProvider: IResourceProvider
) : BaseViewModel<ProductDetailContract.Event, ProductDetailContract.State, ProductDetailContract.Effect>(
    globalState
) {

    private var isInitialized = false
    override fun setInitialState() = ProductDetailContract.State()


    override fun handleEvents(event: ProductDetailContract.Event) {
        when (event) {
            // navigation
            is ProductDetailContract.Event.OnBackIconClicked -> setEffect { ProductDetailContract.Effect.Navigation.GoToBack }
            is ProductDetailContract.Event.OnShareClicked -> shareProduct()
            is ProductDetailContract.Event.OnVisitYourCartClicked -> setEffect { ProductDetailContract.Effect.Navigation.GoToCart }
            is ProductDetailContract.Event.OnSeeMoreBrandClicked -> navigateToBrandItems()
            is ProductDetailContract.Event.OnRelatedItemClicked -> openSelectedProduct(event.itemIndex)
            is ProductDetailContract.Event.OnBuyNowClicked -> addToCart()
            is ProductDetailContract.Event.OnContinueShoppingClicked -> setState { copy(showSuccessAddedToCart = false) }
            is ProductDetailContract.Event.OnTackToUsClicked -> setEffect { ProductDetailContract.Effect.Navigation.GoToTalkToUs }
            // state
            is ProductDetailContract.Event.OnColorItemSelected -> updateColor(event.colorIndex)
            is ProductDetailContract.Event.OnSizeItemSelected -> updateSizeAndItemId(event.sizeIndex)
            ProductDetailContract.Event.OnSeeMoreClicked -> setState { copy(isTextExpanded = isTextExpanded.not()) }
        }
    }

    private fun openSelectedProduct(itemIndex: Int) {
        val product = currentState.productDetail?.relatedItems.orEmpty()[itemIndex]
        setEffect { ProductDetailContract.Effect.Navigation.GoToOpenProduct(product) }
    }

    private fun addToCart() {
        val cartItems = sharedPrefersManager.getProductList().orEmpty().toMutableList()
        val item = currentState.productDetail ?: return
        cartItems.add(item)
        sharedPrefersManager.saveProductList(cartItems)
        setState { copy(showSuccessAddedToCart = true) }
    }

    private fun navigateToBrandItems() {
        val brand = Brand(
            id = currentState.productDetail?.brandId,
            title = currentState.productDetail?.brandTitle,
            imagePath = currentState.productDetail?.imagePath,
        )

        // navigate to brands products list
        setEffect { ProductDetailContract.Effect.Navigation.GoToProductBrandList(brand) }
    }

    private fun shareProduct() {
        val product = currentState.productDetail ?: return

        // share product
        setEffect {
            ProductDetailContract.Effect.ShareProduct(product = product)
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
                productDetail = currentState.productDetail?.copy(selectedItemDetails = selectedItemDetail?.copy(
                    quantity = 1
                ))
            )
        }
    }

    fun init(product: Product) {
        if (isInitialized.not()) {
            loadProductData(product.id.orZero())
            isInitialized = true
        }
    }

    private fun loadProductData(productId: Int) = executeCatching({
        homeUseCase.getAllProductDetails(productId).collect { productDetailResponse ->
            when (productDetailResponse) {
                is Result.Error -> globalState.error(productDetailResponse.message.orEmpty())
                is Result.Loading -> {}
                is Result.Success -> {
                    val productDetail = productDetailResponse.data
                    val selectedItemDetail = productDetail?.itemDetails?.first()
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
                            colorsList = colorsList
                        )
                    }
                }

                else -> {}
            }
        }
    })

    private fun getColorsList(
        colorsIdsList: List<Int?>,
        productDetail: Product?
    ): MutableList<ItemImages> {
        val list = mutableListOf<ItemImages>()
        productDetail?.let { productDetail ->
            colorsIdsList.forEach { colorId ->
                list.add(productDetail.itemImages.firstOrNull { it.colorId == colorId }
                    ?: ItemImages(
                        colorId,
                        colorTitle = productDetail.itemDetails.first { it.colorId == colorId }.colorTitle
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
        colorId: Int,
        productDetail: Product?
    ): List<ItemDetail> =
        productDetail?.itemDetails?.filter { it.colorId == colorId }.orEmpty()
}


