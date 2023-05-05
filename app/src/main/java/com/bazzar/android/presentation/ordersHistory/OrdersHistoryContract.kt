package com.bazzar.android.presentation.ordersHistory

import com.android.model.order.OrderHistory
import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class OrdersHistoryContract {
    class State(
        val orderList: List<OrderHistory>? = listOf(),
        val timeCategoryList: List<String>? = listOf(),
        val selectedTimeCategoryIndex: Int = 0,
    ) : ViewState
    sealed class Event : ViewEvent {
        data class OnTimeCategoryClicked(val index: Int) : Event()
    }
    sealed class Effect : ViewSideEffect { }
}