package com.bazzar.android.presentation.addAddressScreen

import com.android.network.domain.usecases.HomeUseCase
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import javax.inject.Inject

class AddressViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
) : BaseViewModel<AddressContract.Event, AddressContract.State, AddressContract.Effect>(globalState) {
    override fun setInitialState(): AddressContract.State {
        TODO("Not yet implemented")
    }

    override fun handleEvents(event: AddressContract.Event) {
        TODO("Not yet implemented")
    }

    fun init() {
        TODO("Not yet implemented")
    }

}