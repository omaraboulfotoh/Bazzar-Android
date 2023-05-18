package com.bazzar.android.presentation.bazarDetail

import com.android.local.SharedPrefersManager
import com.android.model.home.BazaarModel
import com.android.model.request.SearchProductRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.utils.IResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BazarDetailViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val prefersManager: SharedPrefersManager,
    private val resourceProvider: IResourceProvider,
) :
    BaseViewModel<BazarDetailContract.Event, BazarDetailContract.State, BazarDetailContract.Effect>(
        globalState
    ) {

    private var isInitialized = false
    override fun setInitialState() = BazarDetailContract.State()

    override fun handleEvents(event: BazarDetailContract.Event) {
        when (event) {
            is BazarDetailContract.Event.OnSubCategoryClicked -> onSubCategorySelected(event.categoryIndex)
            BazarDetailContract.Event.OnBackIconClicked -> { setEffect { BazarDetailContract.Effect.Navigation.GoToBack } }
            BazarDetailContract.Event.OnSearchClicked -> {}
            is BazarDetailContract.Event.OnProductClicked -> navigateToProductDetails(event.itemIndex)
        }
    }

    private fun navigateToProductDetails(itemIndex: Int) {
        val item = currentState.productList?.get(itemIndex) ?: return
        // navigate to details
        setEffect { BazarDetailContract.Effect.Navigation.GoToProductDetailPage(item) }
    }

    private fun onSubCategorySelected(subSubCategoryIndex: Int) {}

    fun init(bazaar: BazaarModel) {

        if (isInitialized.not()) {
            val request = SearchProductRequest(marketerId = bazaar.id)
            setState {
                copy(
                    searchRequest = request,
                    bazaar = bazaar
                )
            }
            loadProductData(request)
            isInitialized = true
        }
    }

    private fun loadProductData(request: SearchProductRequest) = executeCatching({
        homeUseCase.getAllProductList(request)
            .collect { productResponse ->
                when (productResponse) {
                    is Result.Error -> globalState.error(productResponse.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> setState {
                        copy(productList = productResponse.data)
                    }

                    else -> {}
                }
            }
    })
}

