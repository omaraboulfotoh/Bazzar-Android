package com.bazzar.android.presentation.cartScreen

import com.android.local.SharedPrefersManager
import com.android.model.home.Product
import com.android.model.request.AddToCartRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.R
import com.bazzar.android.common.orFalse
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.app.ConfirmationDialogParams
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.bazarDetail.BazarDetailContract
import com.bazzar.android.utils.IResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    globalState: IGlobalState,
    private val sharedPrefersManager: SharedPrefersManager,
    private val resourceProvider: IResourceProvider,
    private val homeUseCase: HomeUseCase
) : BaseViewModel<CartContract.Event, CartContract.State, CartContract.Effect>(globalState) {

    private var isInitialized = false
    override fun setInitialState(): CartContract.State = CartContract.State()

    override fun handleEvents(event: CartContract.Event) {
        when (event) {
            CartContract.Event.OnCheckout -> handleCheckout()
            is CartContract.Event.OnDeleteItem -> handleItemAction(
                event.index,
                ItemOperation.DELETE
            )

            is CartContract.Event.OnMinusItem -> handleItemAction(
                event.index,
                ItemOperation.MINUS_ONE
            )

            is CartContract.Event.OnPlusItem -> handleItemAction(event.index, ItemOperation.ADD_ONE)

            is CartContract.Event.OnProductClicked -> handleItemAction(
                event.index,
                ItemOperation.CLICK
            )

            is CartContract.Event.OnProductFavClicked -> handleItemFav(event.index)
            CartContract.Event.OnShoppingClicked -> setEffect { CartContract.Effect.Navigation.GoToHome }
            is CartContract.Event.OnFavProductClicked -> handleFavNavigation(event.index)
            is CartContract.Event.OnProductAddToCartClicked -> addProductToCart(event.index)
        }
    }


    private fun addProductToCart(itemIndex: Int) = executeCatching({
        if (sharedPrefersManager.isUserLongedIn().not()) {
            setEffect { CartContract.Effect.Navigation.GoToLogin }
            return@executeCatching
        }
        val itemDetail = currentState.productWishList?.get(itemIndex) ?: return@executeCatching

        homeUseCase.getAllProductDetails(itemDetail.id.orZero()).collect { response ->
            when (response) {
                is Result.Success -> {
                    val product = response.data!!
                    if (product.itemDetails.size > 1) {
                        setEffect { CartContract.Effect.Navigation.GoToProduct(product = product) }
                    } else if (product.itemDetails.size == 1) {
                        homeUseCase.addToCart(
                            AddToCartRequest(
                                itemDetailId = product.itemDetails.first().id.orZero()
                            )
                        ).collect { response ->
                            when (response) {
                                is Result.Error -> globalState.error(response.message.orEmpty())
                                is Result.Success -> {
                                    loadCart()
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


    private fun handleFavNavigation(index: Int) {
        val productsList = currentState.productWishList.orEmpty().toMutableList()
        val item = productsList[index]
        setEffect { CartContract.Effect.Navigation.GoToProduct(item) }
    }

    private fun handleItemAction(itemIndex: Int, action: ItemOperation) {
        val productsList = currentState.productCartList.orEmpty().toMutableList()
        val item = productsList[itemIndex]
        when (action) {
            ItemOperation.ADD_ONE -> if (item.itemBalance.orZero() > item.qty.orZero()) {
                updateItem(item.itemDetailId.orZero(), item.qty.orZero().plus(1))
            }

            ItemOperation.MINUS_ONE -> if (item.qty.orZero() > 1) {
                updateItem(item.itemDetailId.orZero(), item.qty.orZero().minus(1))
            } else {
                handleItemAction(itemIndex, ItemOperation.DELETE)
            }

            ItemOperation.DELETE -> {
                globalState.confirmationDialog(
                    params = ConfirmationDialogParams(
                        title = resourceProvider.getString(R.string.delete_item),
                        description = resourceProvider.getString(R.string.delete_item_desc),
                        positiveButtonTitle = resourceProvider.getString(R.string.delete),
                        negativeButtonTitle = resourceProvider.getString(R.string.cancel),
                        onPositive = {
                            deleteItem(item.itemDetailId.orZero())
                        },
                    )
                )
            }

            ItemOperation.CLICK -> {
                setEffect { CartContract.Effect.Navigation.GoToProduct(item) }
            }
        }
    }

    private fun deleteItem(itemDetailId: Int) = executeCatching({
        homeUseCase.deleteFromCart(itemDetailId).collect { response ->
            when (response) {
                is Result.Error -> globalState.error(response.message.orEmpty())
                is Result.Success -> loadCart()
                else -> {}
            }
        }
    })

    private fun updateItem(itemDetailId: Int, qty: Int) = executeCatching({
        homeUseCase.updateCartQuantity(itemDetailId, qty).collect { response ->
            when (response) {
                is Result.Error -> globalState.error(response.message.orEmpty())
                is Result.Success -> loadCart()
                else -> {}
            }
        }
    })

    private fun handleCheckout() {
        if (sharedPrefersManager.isUserLongedIn()) {
            setEffect { CartContract.Effect.Navigation.GoToSelectAddress }
        } else {
            setEffect { CartContract.Effect.Navigation.GoToLogin }
        }
    }

    fun init() {
        loadCart()
    }

    private fun loadCart() = executeCatching({
        homeUseCase.loadCart().collect { response ->
            when (response) {
                is Result.Error -> {
                    setState { copy(showEmptyCart = true) }

                    if (sharedPrefersManager.isUserLongedIn()) {
                        loadWishList()
                    }
                }

                is Result.Success -> {
                    handleCartInfo(response.data.orEmpty())
                }

                else -> {}
            }

        }
    })

    private fun handleCartInfo(productsList: List<Product>) {
        val totalCount = productsList.sumOf { it.qty.orZero() }
        val totalAmount =
            productsList.sumOf { it.qty.orZero() * it.price.orZero() }
        setState {
            copy(
                productCartList = productsList,
                counterItem = totalCount,
                totalCartAMount = totalAmount,
                showEmptyCart = productsList.isEmpty()
            )
        }
        if (productsList.isEmpty() && sharedPrefersManager.isUserLongedIn()) {
            loadWishList()
        }
    }

    private fun loadWishList() = executeCatching({
        homeUseCase.getProductWishList().collect { productWishListResponse ->
            when (productWishListResponse) {
                is Result.Success -> {
                    setState {
                        copy(
                            productWishList = productWishListResponse.data.orEmpty(),
                            showWishList = productWishListResponse.data.orEmpty().isEmpty().not()
                        )
                    }
                }

                else -> {}
            }
        }
    })

    private fun handleItemFav(itemIndex: Int) = executeCatching({
        val list = currentState.productWishList?.toMutableList() ?: return@executeCatching
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
                            copy(productWishList = updatedList)
                        }
                    }

                    else -> {}
                }
            }
        } else {
            homeUseCase.deleteBazaarWishList(item.id.orZero()).collect { response ->
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
                            copy(productWishList = updatedList)
                        }
                    }

                    else -> {}
                }
            }
        }

    }, withLoading = false)
}

enum class ItemOperation {
    ADD_ONE, MINUS_ONE, DELETE, CLICK
}