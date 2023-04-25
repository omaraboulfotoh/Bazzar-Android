package com.bazzar.android.presentation.cartScreen

import com.android.network.domain.usecases.HomeUseCase
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import javax.inject.Inject

class CartViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
) : BaseViewModel<CartContract.Event, CartContract.State, CartContract.Effect>(globalState) {
    override fun setInitialState(): CartContract.State = CartContract.State()

    override fun handleEvents(event: CartContract.Event) {
        TODO("Not yet implemented")
    }

    fun init() {
        TODO("Not yet implemented")
    }


}