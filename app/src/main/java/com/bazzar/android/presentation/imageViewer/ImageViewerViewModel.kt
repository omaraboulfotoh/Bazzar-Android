package com.bazzar.android.presentation.imageViewer

import com.android.local.SharedPrefersManager
import com.android.model.home.BazaarModel
import com.android.model.home.Product
import com.android.model.request.AddToCartRequest
import com.android.network.domain.usecases.HomeUseCase
import com.android.network.states.Result
import com.bazzar.android.common.orZero
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.imageViewer.ImageViewerContract.Effect
import com.bazzar.android.presentation.imageViewer.ImageViewerContract.Event
import com.bazzar.android.presentation.imageViewer.ImageViewerContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageViewerViewModel @Inject constructor(
    globalState: IGlobalState,
    private val homeUseCase: HomeUseCase,
    private val sharedPrefersManager: SharedPrefersManager
) : BaseViewModel<Event, State, Effect>(
    globalState
) {
    override fun setInitialState() = State()

    override fun handleEvents(event: Event) {
        when (event) {
            // state
            is Event.OnIndicatorClicked -> setState { copy(currentIndicatorIndex = event.index) }
            is Event.OnBuyNowClicked -> addToCart()
            is Event.OnContinueShoppingClicked -> setState {
                copy(
                    showSuccessAddedToCart = false
                )
            }

            // navigation
            is Event.OnVisitYourCartClicked -> {
                setState {
                    copy(showSuccessAddedToCart = false)
                }
                setEffect { Effect.Navigation.GoToCart }
            }
            is Event.OnBackClicked -> setEffect { Effect.Navigation.GoBack }
        }
    }

    private fun addToCart() = executeCatching({
        if (sharedPrefersManager.isUserLongedIn().not()) {
            setEffect { Effect.Navigation.GoToLogin }
            return@executeCatching
        }

        val itemDetail = currentState.product?.selectedItemDetails ?: return@executeCatching
        homeUseCase.addToCart(
            AddToCartRequest(
                itemDetailId = itemDetail.id.orZero(),
                marketerId = currentState.bazaar?.id
            )
        ).collect { response ->
            when (response) {
                is Result.Error -> globalState.error(response.message.orEmpty())
                is Result.Success -> {
                    setState { copy(showSuccessAddedToCart = true) }
                }

                else -> {}
            }
        }
    })

    fun init(imagePathsList: List<String>, product: Product?, bazaar: BazaarModel?) {
        setState {
            copy(
                imagePathsList = imagePathsList,
                currentIndicatorIndex = 0,
                product = product,
                bazaar = bazaar
            )
        }
    }

}