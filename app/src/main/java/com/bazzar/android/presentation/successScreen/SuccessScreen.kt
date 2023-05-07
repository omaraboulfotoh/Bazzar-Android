package com.bazzar.android.presentation.successScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.bazzar.android.R
import com.bazzar.android.common.navigateAndPopCurrent
import com.bazzar.android.presentation.destinations.OrdersHistoryScreenDestination
import com.bazzar.android.presentation.theme.BazzarTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
@Destination
fun SuccessScreen(navigator: DestinationsNavigator) {

    val coroutinesScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.white),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(painter = painterResource(id = R.drawable.ic_success_big), contentDescription = "")
        Text(
            modifier = Modifier.padding(BazzarTheme.spacing.l),
            text = stringResource(id = R.string.order_success_places),
            style = BazzarTheme.typography.body2Bold,
            color = BazzarTheme.colors.primaryButtonColor,
        )
    }
    LaunchedEffect(Unit) {
        // init logi
        coroutinesScope.launch {
            delay(2000)
            navigator.navigateAndPopCurrent(OrdersHistoryScreenDestination)
        }
    }
}