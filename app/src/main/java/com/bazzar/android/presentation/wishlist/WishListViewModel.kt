package com.bazzar.android.presentation.wishlist

import com.android.model.home.BazaarModel
import com.android.model.home.Product
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    globalState: IGlobalState, private val homeUseCase: HomeUseCase
) : BaseViewModel<WishListContract.Event, WishListContract.State, WishListContract.Effect>(
    globalState
) {

    private var isInitialized = false

    override fun setInitialState() = WishListContract.State()

    override fun handleEvents(event: WishListContract.Event) {
        when (event) {
            WishListContract.Event.OnBackClicked -> {}
            is WishListContract.Event.OnDeleteItem -> {}
            is WishListContract.Event.OnProductClicked -> {}
            is WishListContract.Event.OnBazaarClicked -> {}
            is WishListContract.Event.OnDeleteBazaar -> {}
            is WishListContract.Event.OnProductAddToCartClicked -> {}
            is WishListContract.Event.OnPageChanged -> handlePageChanged(event.pageIndex)
        }
    }

    fun init() {
        if (isInitialized.not()) {

            loadWishList()
            isInitialized = true
        }
    }

    private fun handlePageChanged(pageIndex: Int) {
        // Load tab quotes if not loaded yet
        setState { copy(currentPage = pageIndex) }
        setEffect { WishListContract.Effect.ScrollPager(pageIndex) }
    }

    private fun loadWishList() = executeCatching({
        var bazaarsList = listOf<BazaarModel>()
        var productsList = listOf<Product>()
        homeUseCase.getProductWishList().collect { productWishListResponse ->
            when (productWishListResponse) {
                is Result.Success -> {
                    productsList = productWishListResponse.data.orEmpty()
                }

                else -> {}
            }
            homeUseCase.getBazaarsWishList().collect { bazaarWishListResponse ->
                when (bazaarWishListResponse) {
                    is Result.Success -> {
                        bazaarsList = bazaarWishListResponse.data.orEmpty()
                        setState {
                            copy(
                                productCartList = productsList,
                                bazaarList = bazaarsList,
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    })

}