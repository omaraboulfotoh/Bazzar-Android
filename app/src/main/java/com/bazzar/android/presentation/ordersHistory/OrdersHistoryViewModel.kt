package com.bazzar.android.presentation.ordersHistory

import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersHistoryViewModel @Inject constructor(globalState: IGlobalState) :
    BaseViewModel<OrdersHistoryContract.Event, OrdersHistoryContract.State, OrdersHistoryContract.Effect>(
        globalState
    ) {
    override fun setInitialState(): OrdersHistoryContract.State {
        return OrdersHistoryContract.State()
    }

    override fun handleEvents(event: OrdersHistoryContract.Event) {

    }
}