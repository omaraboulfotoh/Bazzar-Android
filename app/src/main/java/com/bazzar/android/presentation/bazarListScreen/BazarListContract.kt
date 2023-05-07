package com.bazzar.android.presentation.bazarListScreen

import com.android.model.home.Bazar
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class BazarListContract {

    data class State(
        val allBazars: List<Bazar>? = emptyList(),
        val availableBazars: List<Bazar>? = emptyList(),
        val searchTerm: String? = "",
    ) : ViewState

    sealed class Event : ViewEvent {
        data class OnSearchTermChanged(val searchTerm: String) : Event()
        data class OnBazarItemClicked(val bazar: Bazar) : Event()
    }

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            data class GoToBazarDetailsScreen(val bazar: Bazar) : Navigation()
        }
    }
}