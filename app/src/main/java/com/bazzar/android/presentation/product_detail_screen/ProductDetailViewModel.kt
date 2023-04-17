package com.bazzar.android.presentation.product_screen

import androidx.lifecycle.SavedStateHandle
import com.android.model.home.Product
import com.android.model.home.SearchProductRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.Constants.PRODUCT_KEY
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import javax.inject.Inject

class ProductDetailViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase, val savedStateHandle: SavedStateHandle
) :
    BaseViewModel<ProductDetailContract.Event, ProductDetailContract.State, ProductDetailContract.Effect>(
        globalState
    ) {
    private val argumentProduct: Product =
        savedStateHandle.get<Product>(PRODUCT_KEY) ?: Product()

    private var isInitialized = false
    override fun setInitialState() = ProductDetailContract.State()

    override fun handleEvents(event: ProductDetailContract.Event) {
        when (event) {
            is ProductDetailContract.Event.OnBackIconClicked -> TODO()
            is ProductDetailContract.Event.OnFavouriteIconClicked -> TODO()
            is ProductDetailContract.Event.OnBuyNowClicked -> TODO()
            is ProductDetailContract.Event.OnShareClicked -> TODO()
            is ProductDetailContract.Event.OnVisitYourCartClicked -> TODO()
            is ProductDetailContract.Event.OnContinueShoppingClicked -> TODO()
            is ProductDetailContract.Event.OnSeeMoreClicked -> TODO()
            is ProductDetailContract.Event.OnSliderClicked -> TODO()
            is ProductDetailContract.Event.OnRatingClicked -> TODO()
            else -> {}
        }
    }

    fun init() {
        if (isInitialized.not()) {
            setState {
                copy(
                    product = argumentProduct,
//                    relatedProductList =product.
                )
            }
//            loadProductData()
            isInitialized = true
        }
    }

    private fun loadProductData(categoryId: Int) = executeCatching({
        homeUseCase.getAllProductList(SearchProductRequest(categoryId = categoryId))
            .collect { productResponse ->
                when (productResponse) {
                    is Result.Error -> globalState.error(productResponse.message.orEmpty())
                    is Result.Loading -> {}
                    is Result.Success -> setState {
                        copy(
                        )
                    }
                    else -> {}
                }
            }
    })
}

