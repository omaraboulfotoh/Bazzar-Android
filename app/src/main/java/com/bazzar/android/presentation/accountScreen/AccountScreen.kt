package com.bazzar.android.presentation.accountScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.BuildConfig
import com.bazzar.android.common.buildUrlIntent
import com.bazzar.android.common.event.openWebPageInAppBrowser
import com.bazzar.android.common.getActivity
import com.bazzar.android.common.navigateAndClearBackStack
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.SocialMedia
import com.bazzar.android.presentation.accountScreen.composables.AccountScreenContent
import com.bazzar.android.presentation.app.MainActivity
import com.bazzar.android.presentation.destinations.AddressBookScreenDestination
import com.bazzar.android.presentation.destinations.EditProfileScreenDestination
import com.bazzar.android.presentation.destinations.LoginScreenDestination
import com.bazzar.android.presentation.destinations.MainScreenDestination
import com.bazzar.android.presentation.destinations.OrdersHistoryScreenDestination
import com.bazzar.android.presentation.destinations.WishListScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val state = viewModel.viewState()
    val context = LocalContext.current
    val activity = LocalContext.current.getActivity<MainActivity>()!!

    viewModel.sideEffect { effect ->
        when (effect) {
            is AccountContract.Effect.Navigation.GoToEditProfile -> {
                navigator.navigate(EditProfileScreenDestination(userData = effect.userData))
            }

            is AccountContract.Effect.Navigation.GoToOrdersHistory -> {
                navigator.navigate(OrdersHistoryScreenDestination())
            }

            is AccountContract.Effect.Navigation.GoToRegistration -> {
                navigator.navigate(LoginScreenDestination())
            }

            is AccountContract.Effect.Navigation.GoToAddressBook -> {
                navigator.navigate(AddressBookScreenDestination)
            }

            is AccountContract.Effect.Navigation.GoToHome -> {
                navigator.navigateAndClearBackStack(MainScreenDestination())
            }

            AccountContract.Effect.Navigation.GoToAboutUs -> {
                openWebPageInAppBrowser(context, BuildConfig.ABOUT_US)
            }

            AccountContract.Effect.Navigation.GoToContactUs -> {
                openWebPageInAppBrowser(context, BuildConfig.CONTACT_US)
            }

            AccountContract.Effect.Navigation.GoToTermsAndConditions -> {
                openWebPageInAppBrowser(context, BuildConfig.TERMS_AND_CONDITIONS)
            }

            AccountContract.Effect.Navigation.GoToInstagramPage -> {
                buildUrlIntent(SocialMedia.INSTAGRAM_PAGE).apply {
                    context.startActivity(this)
                }
            }

            AccountContract.Effect.Navigation.GoToFacebookPage -> {
                buildUrlIntent(SocialMedia.FACEBOOK_PAGE).apply {
                    context.startActivity(this)
                }
            }

            AccountContract.Effect.Navigation.GoToTwitterPage -> {
                buildUrlIntent(SocialMedia.TWITTER_PAGE).apply {
                    context.startActivity(this)
                }
            }

            AccountContract.Effect.Navigation.OnRestartApp -> MainActivity.restartApp(activity)

            AccountContract.Effect.Navigation.GoToWishList -> navigator.navigate(
                WishListScreenDestination
            )

            is AccountContract.Effect.Navigation.OpenLink -> buildUrlIntent(effect.link).apply {
                context.startActivity(this)
            }
        }
    }
    viewModel.initState()
    AccountScreenContent(state = state) { viewModel.setEvent(it) }
}