package com.bazzar.android.presentation.bazarListScreen

import com.android.model.home.BazaarModel
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class BazarListContract {

    data class State(
        val allBazars: List<BazaarModel>? = emptyList(),
        val availableBazars: List<BazaarModel>? = emptyList(),
        val searchTerm: String? = "",
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnSearchTermChanged(val searchTerm: String) : Event()
        data class OnBazarItemClicked(val bazar: BazaarModel) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToBazarDetailsScreen(val bazar: BazaarModel) : Navigation()
        }
    }
}