package com.bazzar.android.presentation.product_screen

import com.android.model.home.Brand
import com.android.model.home.Category
import com.android.model.home.Product
import com.android.model.home.SearchProductRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import javax.inject.Inject
import kotlin.properties.Delegates

class ProductViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
) :
    BaseViewModel<ProductContract.Event, ProductContract.State, ProductContract.Effect>(
        globalState
    ) {
    var categoryId by Delegates.notNull<Int>()

    private var _brand = Brand()
    var brand
        get() = _brand
        set(value) {
            if (_brand != value) {
                setState { copy(brand = value) }
            }
            _brand = value
        }

    private var _categoryList: List<Category> = emptyList()
    var categoryList
        get() = _categoryList
        set(value) {
            if (_categoryList != value) {
                setState { copy(subCategoryList = value) }
            }
            _categoryList = value
        }

    private var isInitialized = false
    override fun setInitialState() = ProductContract.State()

    override fun handleEvents(event: ProductContract.Event) {
        when (event) {
            is ProductContract.Event.OnSubCategoryClicked -> onSubCategorySelected(event.categoryIndex)

            is ProductContract.Event.OnAddToCartClicked -> TODO()
            ProductContract.Event.OnBackClicked -> ProductContract.Effect.Navigation.back
            is ProductContract.Event.OnFavouriteClicked -> TODO()
            is ProductContract.Event.OnFilterClicked -> filterProductList()
            ProductContract.Event.OnSearchClicked -> {
                setState { copy(isSearchClicked = isSearchClicked.not()) }
            }
            is ProductContract.Event.OnSortClicked -> setState { copy(isSortClicked = isSortClicked.not()) }
            is ProductContract.Event.onSortItemSelected -> sortProductList(event.sortItem)
        }
    }

    private fun onSubCategorySelected(subCategoryIndex: Int) {
        //flip state
        setState { copy(isSubCategoryClicked = isSubCategoryClicked.not()) }
        if (currentState.isSubCategoryClicked) {
            //add subCategory to subCategory List
            setState {
                copy(
                    selectedSubCategoryList = currentState.subCategoryList.orEmpty().filter {
                        it.id == currentState.subCategoryList?.get(subCategoryIndex)?.id
                    }
                )
            }
        }
        //remove subCategory from selectedSubCategoryList
        else {
            setState {
                copy(
                    selectedSubCategoryList = currentState.selectedSubCategoryList.orEmpty()
                        .filterNot {
                            it.id == currentState.selectedSubCategoryList?.get(subCategoryIndex)?.id
                        }
                )
            }

        }
    }

    private fun filterProductList() {
        val categoryIds = currentState.subCategoryList?.map { it.id }?.toHashSet() ?: return
        setState {
            copy(
                filteredProductList = productList?.filter { product: Product ->
                    categoryIds.contains(product.categoryId)
                }
            )
        }
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
            loadProductData()
            isInitialized = true
        }
    }

    private fun loadProductData() = executeCatching({
        homeUseCase.getAllProductList(SearchProductRequest(categoryId = categoryId))
            .collect { productResponse ->
                when (productResponse) {
                    is Result.Error -> globalState.error(productResponse.message.orEmpty())
                    is Result.Loading -> {}
                    is Result.Success -> setState {
                        copy(
                            productList = productResponse.data
                        )
                    }
                    else -> {}
                }
            }
    })

}