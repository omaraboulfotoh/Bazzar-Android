package com.bazzar.android.presentation.productDetail

import com.android.model.home.Brand
import com.android.model.home.Product
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
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

            is ProductDetailContract.Event.OnVisitYourCartClicked -> {}
            is ProductDetailContract.Event.OnSeeMoreClicked -> navigateToBrandItems()
            is ProductDetailContract.Event.OnRelatedItemClicked -> openSelectedProduct(event.itemIndex)
            is ProductDetailContract.Event.OnBuyNowClicked -> addToCart()
            // state
            is ProductDetailContract.Event.OnContinueShoppingClicked -> dismissDialog()
            is ProductDetailContract.Event.OnColorItemSelected -> updateColor(event.colorIndex)
            is ProductDetailContract.Event.OnSizeItemSelected -> updateSizeAndItemId(event.sizeIndex)
        }
    }

    private fun openSelectedProduct(itemIndex: Int) {
        val product = currentState.productDetail?.relatedItems.orEmpty().get(itemIndex) ?: return
        setEffect { ProductDetailContract.Effect.Navigation.GoToOpenProduct(product) }
    }

    private fun addToCart() {
    }

    private fun dismissDialog() {
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
        setState {
            copy(
                selectedItemDetailId = currentState.productDetail?.itemDetails?.find {
                    it.sizeTitle == selectedSizeTitleList[sizeIndex] && it.colorId == selectedColorId
                }?.id.orZero()
            )
        }
    }

    private fun updateColor(colorIndex: Int) {
        setState {
            copy(
                // update colorId
                selectedColorId = currentState.productDetail?.itemImages?.get(colorIndex)?.colorId.orZero(),
                // update slider images
                selectedColoredImagesList = filterColorImagesWithId(selectedColorId.orZero()).orEmpty()
                    .map { it.imagePath ?: "" },
                //  update size with respect to color
                selectedSizeTitleList = filterSizeWithColorIdsList(selectedColorId.orZero()).orEmpty(),
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
                    setState {
                        copy(
                            productDetail = productDetail,
                        )
                    }
                    val selectedImagedList = filterColorImagesWithId(
                        selectedItemDetail?.colorId.orZero()
                    )?.map { it.imagePath.orEmpty() }
                    val selectedTitleList = filterSizeWithColorIdsList(
                        selectedItemDetail?.colorId.orZero()
                    )

                    setState {
                        copy(
                            selectedItemDetailId = selectedItemDetail?.id.orZero(),
                            selectedColoredImagesList = selectedImagedList.orEmpty(),
                            selectedSizeTitleList = selectedTitleList.orEmpty()
                        )
                    }
                }

                else -> {}
            }
        }
    })

    private fun filterColorImagesWithId(colorId: Int) =
        currentState.productDetail?.itemImages?.filter { it.colorId == colorId }

    private fun filterSizeWithColorIdsList(colorId: Int) =
        currentState.productDetail?.itemDetails?.filter { it.colorId == colorId }
            ?.map { it.sizeTitle.orEmpty() }
}

