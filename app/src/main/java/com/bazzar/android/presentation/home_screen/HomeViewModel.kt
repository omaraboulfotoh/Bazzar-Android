package com.bazzar.android.presentation.home_screen

import com.android.network.domain.repos.HomeRepo
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
) :
    BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>(
        globalState
    ) {

    private var isInitialized = false
    override fun setInitialState(): HomeContract.State {
        return HomeContract.State()
    }

    override fun handleEvents(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnSliderClicked -> handleSliderAction(event.sliderIndex)
            else -> {}
        }
    }

    private fun handleSliderAction(sliderIndex: Int) {
        // TODO: will handle the slider action
    }

    fun init() {
        if (isInitialized.not()) {
            loadHomeData()
            isInitialized = true
        }
    }

    private fun loadHomeData() = executeCatching({
        homeUseCase.getHome().collect {
            when (it) {
                is Result.Error -> globalState.error(it.message.orEmpty())
                is Result.Loading -> {}
                is Result.Success -> setState {
                    copy(
                        slides1 = it.data?.slider1,
                        slides2 = it.data?.slider2,
                        categoryItems = it.data?.categoryItems,
                        featuredBrands = it.data?.featuredBrands,
                        featuredCategories = it.data?.featuredCategories
                    )
                }
                else -> {}
            }
        }
    })

}