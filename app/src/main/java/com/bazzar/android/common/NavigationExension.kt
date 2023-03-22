package com.bazzar.android.common

import com.bazzar.android.presentation.NavGraphs
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.Direction

fun DestinationsNavigator.navigateAndClearBackStack(direction: Direction) {
    navigate(direction) {
        popUpTo(NavGraphs.root) {
            inclusive = true
        }
    }
}

fun DestinationsNavigator.navigateAndPopCurrent(direction: Direction) {
    navigate(direction) {
        popBackStack()
    }
}

fun DestinationsNavigator.popTo(directionRoute: String) {
    popBackStack(
        route = directionRoute,
        inclusive = false,
        saveState = false
    )
}