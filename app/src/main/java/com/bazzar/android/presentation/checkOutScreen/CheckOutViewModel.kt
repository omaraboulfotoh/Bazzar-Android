package com.bazzar.android.presentation.checkOutScreen

import com.android.network.domain.usecases.HomeUseCase
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import javax.inject.Inject

class CheckOutViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
) : BaseViewModel<CheckOutContract.Event, CheckOutContract.State, CheckOutContract.Effect>(
    globalState
) {
    override fun setInitialState(): CheckOutContract.State {
        TODO("Not yet implemented")
    }

    override fun handleEvents(event: CheckOutContract.Event) {
        TODO("Not yet implemented")
    }

    fun init() {
        TODO("Not yet implemented")
    }
}