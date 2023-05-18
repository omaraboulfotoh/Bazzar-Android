package com.bazzar.android.presentation.wishlist

import com.android.model.home.BazaarModel
import com.android.model.home.Product
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.common.orZero
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
            WishListContract.Event.OnBackClicked -> setEffect { WishListContract.Effect.Navigation.GoToBack }
            is WishListContract.Event.OnDeleteItem -> handleDeleteItemFromWishList(event.index)
            is WishListContract.Event.OnProductClicked -> navigateToProduct(event.index)
            is WishListContract.Event.OnBazaarClicked -> setEffect {
                WishListContract.Effect.Navigation.GoToBazaar(
                    event.bazaar
                )
            }

            is WishListContract.Event.OnDeleteBazaar -> handleDeleteBazarFromWishList(event.bazaar)
            is WishListContract.Event.OnProductAddToCartClicked -> handleAddToCart(event.index)
            is WishListContract.Event.OnPageChanged -> handlePageChanged(event.pageIndex)
        }
    }

    private fun handleAddToCart(index: Int) = executeCatching({


        val item = currentState.productCartList?.get(index) ?: return@executeCatching
        homeUseCase.getAllProductDetails(item.id.orZero()).collect { response ->
            when (response) {
                is Result.Success -> {
                    // todo will show add to cart sheet
                }

                else -> {}
            }

        }
    })

    private fun navigateToProduct(index: Int) {
        val item = currentState.productCartList?.get(index) ?: return
        setEffect { WishListContract.Effect.Navigation.GoToProduct(item) }
    }

    private fun handleDeleteItemFromWishList(index: Int) = executeCatching({
        val list = currentState.productCartList?.toMutableList() ?: return@executeCatching
        val item = list[index]
        homeUseCase.deleteProductWishList(item.id.orZero()).collect { response ->
                when (response) {
                    is Result.Success -> {
                        list.removeAt(index)
                        setState {
                            copy(
                                productCartList = list, showEmptyProducts = list.isEmpty()
                            )
                        }
                    }

                    else -> {}
                }
            }
    })

    private fun handleDeleteBazarFromWishList(bazzar: BazaarModel) = executeCatching({
        val list = currentState.bazaarList?.toMutableList() ?: return@executeCatching
        val index = list.indexOf(bazzar)
        val item = list[index]
        homeUseCase.deleteBazaarWishList(item.id.orZero()).collect { response ->
                when (response) {
                    is Result.Success -> {
                        list.removeAt(index)
                        setState {
                            copy(
                                bazaarList = list, showEmptyBazaars = bazaarList.orEmpty().isEmpty()
                            )
                        }
                    }

                    else -> {}
                }
            }
    })

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
                                showEmptyProducts = productsList.isEmpty(),
                                bazaarList = bazaarsList,
                                showEmptyBazaars = bazaarsList.isEmpty()
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    })

}