package com.bazzar.android.presentation.ordersHistory

import com.bazzar.android.presentation.base.ViewEvent
import com.bazzar.android.presentation.base.ViewSideEffect
import com.bazzar.android.presentation.base.ViewState

class OrdersHistoryContract {
    class State : ViewState
    sealed class Event : ViewEvent { }
    sealed class Effect : ViewSideEffect { }
}