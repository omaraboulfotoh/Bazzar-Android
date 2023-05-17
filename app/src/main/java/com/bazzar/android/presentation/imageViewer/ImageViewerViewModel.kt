package com.bazzar.android.presentation.imageViewer

import com.android.local.SharedPrefersManager
import com.android.model.home.Product
import com.bazzar.android.presentation.app.IGlobalState
import com.bazzar.android.presentation.base.BaseViewModel
import com.bazzar.android.presentation.imageViewer.ImageViewerContract.State
import com.bazzar.android.presentation.imageViewer.ImageViewerContract.Event
import com.bazzar.android.presentation.imageViewer.ImageViewerContract.Effect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageViewerViewModel @Inject constructor(
    globalState: IGlobalState,
    private val sharedPrefersManager: SharedPrefersManager,
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
            is Event.OnVisitYourCartClicked -> setEffect { Effect.Navigation.GoToCart }
            is Event.OnBackClicked -> setEffect { Effect.Navigation.GoBack }
        }
    }

    private fun addToCart() {
        val cartItems = sharedPrefersManager.getProductList().orEmpty().toMutableList()
        val item = currentState.product ?: return
        cartItems.add(item)
        sharedPrefersManager.saveProductList(cartItems)
        setState { copy(showSuccessAddedToCart = true) }
    }

    fun init(imagePathsList: List<String>, product: Product?) {
        setState {
            copy(
                imagePathsList = imagePathsList,
                currentIndicatorIndex = 0,
                product = product
            )
        }
    }

}