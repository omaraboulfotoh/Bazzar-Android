package com.bazzar.android.presentation.app

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bazzar.android.R
import com.bazzar.android.presentation.NavGraphs
import com.bazzar.android.presentation.common.Progress
import com.bazzar.android.presentation.composables.ConfirmationMessageDialog
import com.bazzar.android.presentation.composables.DefaultMessageDialog
import com.bazzar.android.presentation.theme.BazzarComposeTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun App(globalState: IGlobalState) {

    val engine = rememberAnimatedNavHostEngine(
        navHostContentAlignment = Alignment.TopCenter,
        rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING,
    )
    val navController = engine.rememberNavController()

    BazzarComposeTheme {
        AppScaffold(
            navController = navController,
        ) {
            DestinationsNavHost(
                engine = engine,
                navController = navController,
                navGraph = NavGraphs.root,
                modifier = Modifier.padding(it),
                startRoute = NavGraphs.root.startRoute
            )

            if (globalState.loadingState.value) {
                Progress()
            }

            if (globalState.errorState.value != null) {
                DefaultMessageDialog(
                    title = stringResource(R.string.error),
                    body = globalState.errorState.value!!,
                    buttonText = stringResource(R.string.got_it),
                    onNegative = {
                        globalState.idle()
                    },
                    onPositive = {
                        globalState.idle()
                    }
                )
            }
            if (globalState.errorStateRes.value != null) {
                DefaultMessageDialog(
                    title = stringResource(R.string.error),
                    body = stringResource(id = globalState.errorStateRes.value!!),
                    buttonText = stringResource(R.string.got_it),
                    onNegative = {
                        globalState.idle()
                    },
                    onPositive = {
                        globalState.idle()
                    }
                )
            }

            if (globalState.successState.value != null) {
                DefaultMessageDialog(
                    title = stringResource(R.string.success),
                    body = globalState.successState.value!!,
                    buttonText = stringResource(R.string.got_it),
                    onNegative = {
                        globalState.idle()
                    },
                    onPositive = {
                        globalState.idle()
                    }
                )
            }
            if (globalState.successStateRes.value != null) {
                DefaultMessageDialog(
                    title = stringResource(R.string.success),
                    body = stringResource(id = globalState.successStateRes.value!!),
                    buttonText = stringResource(R.string.got_it),
                    onNegative = {
                        globalState.idle()
                    },
                    onPositive = {
                        globalState.idle()
                    }
                )
            }

            if (globalState.confirmationState.value != null) {
                val state = globalState.confirmationState.value!!
                ConfirmationMessageDialog(
                    title = stringResource(id = state.title),
                    body = stringResource(id = state.description),
                    positiveButtonText = stringResource(id = state.positiveButtonTitle),
                    negativeButtonText = stringResource(id = state.negativeButtonTitle),
                    onNegative = {
                        globalState.idle()
                        state.onNegative?.invoke()
                    },
                    onPositive = {
                        globalState.idle()
                        state.onPositive.invoke()
                    }
                )
            }
        }
    }
}
