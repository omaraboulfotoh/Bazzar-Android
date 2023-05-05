package com.bazzar.android.presentation.ordersHistory

import com.android.local.SharedPrefersManager
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class OrdersHistoryViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val prefersManager: SharedPrefersManager,
) :
    BaseViewModel<OrdersHistoryContract.Event, OrdersHistoryContract.State, OrdersHistoryContract.Effect>(
        globalState
    ) {
    override fun setInitialState() = OrdersHistoryContract.State()

    override fun handleEvents(event: OrdersHistoryContract.Event) {

    }

    fun init() = executeCatching({
        val lang = prefersManager.getAppLanguage() == SharedPrefersManager.LANGUAGE_AR
        homeUseCase.getOrdersHistory(lang)
            .collect {ordersResult ->
                when (ordersResult) {
                    is Result.Error -> globalState.error(ordersResult.message.orEmpty())
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> {

                    }
                }
            }
    })
}