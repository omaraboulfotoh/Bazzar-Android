package com.bazzar.android.presentation.search

import com.android.local.SharedPrefersManager
import com.android.model.request.AddToCartRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.common.orFalse
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.main.MainContract
import com.bazzar.android.presentation.search.SearchScreenContract.Effect
import com.bazzar.android.presentation.search.SearchScreenContract.Event
import com.bazzar.android.presentation.search.SearchScreenContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val prefersManager: SharedPrefersManager,
) : BaseViewModel<Event, State, Effect>(globalState) {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.OnRecentSearchClicked -> {
                setEffect { Effect.Navigation.GoToProductScreen(event.searchTerm) }
            }

            is Event.OnProductClicked -> onProductClicked(event.index)
            is Event.OnRemoveRecentSearchClicked -> handleRemoveRecentSearch(event.index)
            is Event.OnSearchTermChanged -> setState { copy(searchTerm = event.searchTerm) }
            is Event.OnSearchClicked -> handleSearchClick()
            is Event.OnBackClicked -> setEffect { Effect.Navigation.GoBack }
            is Event.OnProductAddToCartClicked -> addProductToCart(event.index)
            is Event.OnRelatedItemFavClicked -> handleProductFav(event.index)
            Event.OnContinueShoppingClicked -> setState {
                copy(showSuccessAddedToCart = false)
            }

            Event.OnVisitYourCartClicked -> {
                setState { copy(showSuccessAddedToCart = false) }
                publishMainEventBut(MainContract.CART_TAB)
            }
        }
    }

    private fun onProductClicked(index: Int) {
        val product = currentState.productList?.get(index)
        product?.let {
            setEffect { Effect.Navigation.GoToProductDetails(it) }
        }
    }

    private fun handleRemoveRecentSearch(index: Int) {
        val recentSearchList = ArrayList(currentState.recentSearchList ?: listOf())
        recentSearchList.removeAt(index)
        prefersManager.saveRecentSearchList(recentSearchList)
        setState { copy(recentSearchList = recentSearchList) }
    }

    private fun handleSearchClick() {
        val searchTerm = currentState.searchTerm
        if (searchTerm.isNullOrEmpty()) return

        val recentSearchList = ArrayList(currentState.recentSearchList ?: listOf())
        val isExist =
            recentSearchList.find { it.equals(searchTerm, ignoreCase = true) }.isNullOrEmpty().not()
        if (isExist.not()) {
            recentSearchList.add(searchTerm.trim())
            prefersManager.saveRecentSearchList(recentSearchList)
        }
        setEffect { Effect.Navigation.GoToProductScreen(searchTerm) }
    }

    private fun addProductToCart(itemIndex: Int) = executeCatching({
        if (prefersManager.isUserLongedIn().not()) {
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
                                itemDetailId = product.itemDetails?.first()?.id.orZero()
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
            setEffect { Effect.Navigation.GoToLogin }
            return@executeCatching
        }
        val list = currentState.productList?.toMutableList() ?: return@executeCatching
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


    fun init() = executeCatching({
        val recentSearchList = prefersManager.getRecentSearchList()
        setState { copy(recentSearchList = recentSearchList) }
        homeUseCase.getTopRated().collect {
            when (it) {
                is Result.Error -> {}
                is Result.Loading -> globalState.loading(true)
                is Result.Success -> {
                    setState { copy(productList = it.data) }
                }
            }
        }
    })
}