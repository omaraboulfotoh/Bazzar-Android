package com.bazzar.android.presentation.home_screen

import com.android.model.movie.MovieModel
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(globalState: IGlobalState) :
    BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>(
        globalState
    ) {
    override fun setInitialState(): HomeContract.State {
        return HomeContract.State
    }

    override fun handleEvents(event: HomeContract.Event) {
        when (event) {
            HomeContract.Event.StartScreen -> getData()
        }
    }

    fun getData(): List<MovieModel> {
        setEffect {
            HomeContract.Effect.Navigation.GoToHome
        }
        return listOf(MovieModel())
    }

}