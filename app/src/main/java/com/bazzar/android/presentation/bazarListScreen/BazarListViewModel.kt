package com.bazzar.android.presentation.bazarListScreen

import com.android.local.SharedPrefersManager
import com.android.model.home.BazaarModel
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.common.orFalse
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.bazarListScreen.BazarListContract.Effect
import com.bazzar.android.presentation.bazarListScreen.BazarListContract.Event
import com.bazzar.android.presentation.bazarListScreen.BazarListContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BazarListViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val sharedPrefersManager: SharedPrefersManager
) : BaseViewModel<Event, State, Effect>(globalState) {

    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.OnSearchTermChanged -> handleSearching(event.searchTerm)
            is Event.OnBazarItemClicked -> setEffect {
                Effect.Navigation.GoToBazarDetailsScreen(
                    event.bazar
                )
            }

            is Event.OnBazarItemFavClicked -> handleBazaarFav(event.bazar)
        }
    }

    private fun handleBazaarFav(bazar: BazaarModel) = executeCatching({
        if (sharedPrefersManager.isUserLongedIn().not()) {
            setEffect { Effect.Navigation.GoToLogin }
            return@executeCatching
        }
        val isFav = bazar.isWishList.orFalse().not()
        if (isFav) {
            homeUseCase.addBazaarWishList(bazar.id.orZero())
                .collect { response ->
                    when (response) {
                        is Result.Success -> {
                            val updatedList = currentState.allBazars.orEmpty().map { item ->
                                if (item.id == bazar.id) {
                                    item.copy(isWishList = response.data.orFalse())
                                } else {
                                    item
                                }
                            }
                            val updatedAvailableList =
                                currentState.availableBazars.orEmpty().map { item ->
                                    if (item.id == bazar.id) {
                                        item.copy(isWishList = response.data.orFalse())
                                    } else {
                                        item
                                    }
                                }
                            setState {
                                copy(
                                    allBazars = updatedList,
                                    availableBazars = updatedAvailableList
                                )
                            }
                        }

                        else -> {}
                    }
                }
        } else {
            homeUseCase.deleteBazaarWishList(bazar.id.orZero())
                .collect { response ->
                    when (response) {
                        is Result.Success -> {
                            val updatedList = currentState.allBazars.orEmpty().map { item ->
                                if (item.id == bazar.id) {
                                    item.copy(isWishList = response.data.orFalse().not())
                                } else {
                                    item
                                }
                            }
                            val updatedAvailableList =
                                currentState.availableBazars.orEmpty().map { item ->
                                    if (item.id == bazar.id) {
                                        item.copy(isWishList = response.data.orFalse().not())
                                    } else {
                                        item
                                    }
                                }
                            setState {
                                copy(
                                    allBazars = updatedList,
                                    availableBazars = updatedAvailableList
                                )
                            }
                        }

                        else -> {}
                    }
                }
        }

    }, withLoading = false)

    private fun handleSearching(term: String) {
        val matchedList = currentState.allBazars?.filter {
            it.name?.contains(term, ignoreCase = true) == true
        }

        setState { copy(searchTerm = term, availableBazars = matchedList) }
    }

    fun getAllBazars() = executeCatching({
        homeUseCase.getAllBazars()
            .collect {
                when (it) {
                    is Result.Error -> globalState.error(it.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> {
                        val allBazars = it.data.orEmpty()
                        setState {
                            copy(
                                allBazars = allBazars,
                                availableBazars = ArrayList(allBazars)
                            )
                        }
                    }
                }
            }
    })

}