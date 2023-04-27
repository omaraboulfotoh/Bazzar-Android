package com.bazzar.android.presentation.product_detail_screen

import com.android.model.home.ProductDetail
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
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
            is ProductDetailContract.Event.OnBackIconClicked -> {}
            is ProductDetailContract.Event.OnShareClicked -> {}
            is ProductDetailContract.Event.OnVisitYourCartClicked -> {}
            is ProductDetailContract.Event.OnSeeMoreClicked -> {}
            is ProductDetailContract.Event.OnRelatedItemClicked -> {}
            is ProductDetailContract.Event.OnBuyNowClicked -> {}

            // state
            is ProductDetailContract.Event.OnFavouriteIconClicked -> {}
            is ProductDetailContract.Event.OnContinueShoppingClicked -> {}
            is ProductDetailContract.Event.OnSliderClicked -> {}
            is ProductDetailContract.Event.OnRatingClicked -> {}
            is ProductDetailContract.Event.OnColorItemSelected -> updateColor(event.colorIndex)
            is ProductDetailContract.Event.OnSizeItemSelected -> updateSizeAndItemId(event.sizeIndex)
        }
    }

    private fun updateSizeAndItemId(sizeIndex: Int) {
        setState {
            copy(
                selectedItemDetailId = currentState.productDetail.itemDetails.find {
                    it.sizeTitle == selectedSizeTitleList[sizeIndex] && it.colorId == selectedColorId
                }?.id
                    ?: -1
            )
        }
    }

    private fun updateColor(colorIndex: Int) {
        setState {
            copy(
                // update colorId
                selectedColorId = currentState.productDetail.itemImages[colorIndex].colorId ?: -1,
                // update slider images
                selectedColoredImagesList = filterColorImagesWithId(selectedColorId).map {
                    it.imagePath ?: ""
                },
                //  update size with respect to color
                selectedSizeTitleList = filterSizeWithColorIdsList(selectedColorId),
            )
        }
    }

    fun init(productId: Int?) {
        if (isInitialized.not()) {
            productId?.let {
                loadProductData(productId)
            }
            isInitialized = true
        }
    }

    private fun loadProductData(productId: Int) = executeCatching({
        homeUseCase.getAllProductDetails(productId).collect { productDetailResponse ->
            when (productDetailResponse) {
                is Result.Error -> globalState.error(productDetailResponse.message.orEmpty())
                is Result.Loading -> {}
                is Result.Success -> {
                    val productDetail = productDetailResponse.data ?: ProductDetail()
                    val selectedItemDetail = productDetail.itemDetails.first()
                    setState {
                        copy(
                            productDetail = productDetail,
                            selectedItemDetailId = selectedItemDetail.id ?: -1,
                            selectedColoredImagesList = filterColorImagesWithId(
                                selectedItemDetail.colorId ?: -1
                            ).map { it.imagePath ?: "" },
                            selectedSizeTitleList = filterSizeWithColorIdsList(
                                selectedItemDetail.colorId ?: -1
                            )
                        )
                    }
                }
                else -> {}
            }
        }
    })

    private fun filterColorImagesWithId(colorId: Int) =
        currentState.productDetail.itemImages.filter { it.colorId == colorId }

    private fun filterSizeWithColorIdsList(colorId: Int) =
        currentState.productDetail.itemDetails.filter { it.colorId == colorId }
            .map { it.sizeTitle ?: "" }
}

