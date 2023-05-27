package com.bazzar.android.presentation.aboutUs

import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.aboutUs.AboutUsContract.Effect
import com.bazzar.android.presentation.aboutUs.AboutUsContract.Event
import com.bazzar.android.presentation.aboutUs.AboutUsContract.State
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AboutUsViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase
) : BaseViewModel<Event, State, Effect>(globalState) {
    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            Event.OnBackClicked -> setEffect { Effect.Navigation.GoToBack }
        }
    }

    fun init() = executeCatching({

        homeUseCase.getAboutUs().collect {
            when (it) {
                is Result.Error -> globalState.error(it.message.orEmpty())
                is Result.Success -> setState {
                    copy(aboutUsContent = it.data.orEmpty())
                }

                else -> {}
            }
        }
    })

}