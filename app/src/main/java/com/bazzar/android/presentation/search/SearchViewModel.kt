package com.bazzar.android.presentation.search

import com.android.local.SharedPrefersManager
import com.android.model.request.SearchProductRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
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

    fun init() = executeCatching({
        val recentSearchList = prefersManager.getRecentSearchList()
        setState { copy(recentSearchList = recentSearchList) }
        homeUseCase.getAllProductList(SearchProductRequest(categoryId = 0)).collect {
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