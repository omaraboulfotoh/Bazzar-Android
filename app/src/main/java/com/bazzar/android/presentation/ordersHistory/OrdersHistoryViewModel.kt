package com.bazzar.android.presentation.ordersHistory

import com.android.local.SharedPrefersManager
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.R
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.utils.IResourceProvider
import com.bazzar.android.utils.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrdersHistoryViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val prefersManager: SharedPrefersManager,
    private val resourceProvider: IResourceProvider
) :
    BaseViewModel<OrdersHistoryContract.Event, OrdersHistoryContract.State, OrdersHistoryContract.Effect>(
        globalState
    ) {
    override fun setInitialState() = OrdersHistoryContract.State()

    override fun handleEvents(event: OrdersHistoryContract.Event) {
        when (event) {
            is OrdersHistoryContract.Event.OnBackIconClicked ->
                setEffect { OrdersHistoryContract.Effect.Navigation.GoToBack }

            else -> {}
        }
    }

    fun init() = executeCatching({
        homeUseCase.getOrdersHistory()
            .collect { ordersResult ->
                when (ordersResult) {
                    is Result.Loading -> globalState.loading(true)
                    is Result.Success -> {
                        setState {
                            copy(
                                orderList = ordersResult.data.orEmpty(),
                                selectedTimeCategoryIndex = 0,
                                orderListPerTime = ordersResult.data.orEmpty(),
                                timeCategoryList = listOf(
                                    resourceProvider.getString(R.string.all),
                                    resourceProvider.getString(R.string.last_week),
                                    resourceProvider.getString(R.string.last_month)
                                )
                            )
                        }
                    }

                    else -> {}
                }
            }
    })
}