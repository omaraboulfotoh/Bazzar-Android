package com.bazzar.android.presentation.addressBookScreen

import com.android.network.domain.usecases.HomeUseCase
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import javax.inject.Inject

class AddressBookViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
) : BaseViewModel<AddressBookContract.Event, AddressBookContract.State, AddressBookContract.Effect>(
    globalState
) {
    override fun setInitialState(): AddressBookContract.State {
        TODO("Not yet implemented")
    }

    override fun handleEvents(event: AddressBookContract.Event) {
        TODO("Not yet implemented")
    }

    fun init() {
        TODO("Not yet implemented")
    }
}