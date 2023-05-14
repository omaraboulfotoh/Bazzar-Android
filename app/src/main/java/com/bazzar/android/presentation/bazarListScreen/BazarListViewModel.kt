package com.bazzar.android.presentation.bazarListScreen

import com.android.local.SharedPrefersManager
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
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
            is Event.OnBazarItemClicked -> setEffect { Effect.Navigation.GoToBazarDetailsScreen(event.bazar) }
        }
    }

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
                        setState { copy(allBazars = allBazars, availableBazars = ArrayList(allBazars)) }
                    }
                }
            }
    })

}