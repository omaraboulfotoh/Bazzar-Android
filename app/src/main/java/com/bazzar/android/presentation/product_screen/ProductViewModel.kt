package com.bazzar.android.presentation.product_screen

import androidx.lifecycle.SavedStateHandle
import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.Product
import com.android.model.home.SearchProductRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.Constants.BRAND_KEY
import com.bazzar.android.presentation.Constants.MAIN_CATEGORY_LIST_KEY
import com.bazzar.android.presentation.Constants.SUB_CATEGORY_ID_KEY
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase, val savedStateHandle: SavedStateHandle
) :
    BaseViewModel<ProductContract.Event, ProductContract.State, ProductContract.Effect>(
        globalState
    ) {



    private val mainCategoryList: List<Category> =
        savedStateHandle.get<List<Category>>(MAIN_CATEGORY_LIST_KEY) ?: emptyList()

    private val subCategoryId: Int = savedStateHandle.get<Int>(SUB_CATEGORY_ID_KEY) ?: 0
    private val selectedBrand: Brand = savedStateHandle.get<Brand>(BRAND_KEY) ?: Brand()

    private var isInitialized = false
    override fun setInitialState() = ProductContract.State()

    override fun handleEvents(event: ProductContract.Event) {
        when (event) {
            is ProductContract.Event.OnSubSubCategoryClicked -> onSubCategorySelected(event.categoryIndex)
            is ProductContract.Event.OnAddToCartClicked -> TODO()
            ProductContract.Event.OnBackIconClicked -> ProductContract.Effect.Navigation.back
            is ProductContract.Event.OnFavouriteIconClicked -> TODO()
            is ProductContract.Event.OnFilterApplied -> filterProductList()
            ProductContract.Event.OnSearchClicked -> {setState {copy(isSearchClicked = isSearchClicked.not()) }}
            is ProductContract.Event.OnSortIconClicked -> TODO()
            is ProductContract.Event.OnSortApplied -> sortProductList(event.sortItem)
            ProductContract.Event.OnFilterIconClicked -> TODO()
        }
    }

    private fun onSubCategorySelected(subSubCategoryIndex: Int) {
        //flip state
        setState { copy(isSubSubCategoryClicked = isSubSubCategoryClicked.not()) }
        if (currentState.isSubSubCategoryClicked) {
            // get subSubCategory products and show them
            loadProductData(currentState.subSubCategoryList?.get(subSubCategoryIndex)?.id ?: return)
        }
        //remove subSubCategory product from product shown
        else {
            setState {
                copy(
                    productList = productList?.filterNot {
                        it.id == (subSubCategoryList?.get(
                            subSubCategoryIndex
                        )?.id)
                    }
                )
            }

        }
    }

    private fun filterProductList() {
/*
        setState {
            copy(
                filteredProductList = productList?.filter { product: Product ->
                    categoryIds.contains(product.categoryId)
                }
            )
        }
*/
    }

    private fun sortProductList(sortItem: ProductContract.State.SortingValues) {
        //if user sort without filtering
        if (currentState.filteredProductList.isNullOrEmpty()) {
            currentState.filteredProductList = currentState.productList
        }
        when (sortItem) {
            ProductContract.State.SortingValues.NO_SORTING -> {}
            ProductContract.State.SortingValues.PRICE_HIGH_TO_LOW -> setState {
                copy(
                    filteredProductList = filteredProductList?.sortedByDescending { it.price })
            }
            ProductContract.State.SortingValues.PRICE_LOW_TO_HEIGH -> setState {
                copy(
                    filteredProductList = filteredProductList?.sortedBy { it.price })
            }
            ProductContract.State.SortingValues.DISCOUNT_ONLY -> setState { copy(filteredProductList = filteredProductList?.filter { it.price != it.oldPrice }) }
            ProductContract.State.SortingValues.NEW_ARRIVAL -> setState { copy(filteredProductList = filteredProductList?.filter { it.isNew }) }
            ProductContract.State.SortingValues.BAZAAAR_PICKS -> TODO()
        }
    }

    fun init() {
        if (isInitialized.not()) {
            setState {
                copy(
                    subSubCategoryList = mainCategoryList.filter { it.id == subCategoryId },
                    brand = selectedBrand
                )
            }
            loadProductData(subCategoryId)
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
                            productList = currentState.productList?.plus(productResponse.data) as List<Product>
                        )
                    }
                    else -> {}
                }
            }
    })
}

