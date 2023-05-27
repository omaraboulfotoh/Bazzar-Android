package com.bazzar.android.presentation.aboutUs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.R
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.HtmlText
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.theme.BazzarTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun AboutUsScreen(
    viewModel: AboutUsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {

    val state = viewModel.viewState()
    val scrollState = rememberScrollState()

    viewModel.sideEffect { effect ->
        when (effect) {
            AboutUsContract.Effect.Navigation.GoToBack -> navigator.navigateUp()
        }
    }

    viewModel.init()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = BottomNavigationHeight),
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
    ) {
        BazzarAppBar(title = stringResource(id = R.string.about_us), onNavigationClick = {
            viewModel.setEvent(AboutUsContract.Event.OnBackClicked)
        })
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(BazzarTheme.spacing.m)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            HtmlText(html = state.aboutUsContent, modifier = Modifier.fillMaxSize())
        }
    }
}