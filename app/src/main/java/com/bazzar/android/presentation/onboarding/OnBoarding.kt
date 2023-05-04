@file:OptIn(ExperimentalPagerApi::class, ExperimentalPagerApi::class)

package com.bazzar.android.presentation.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.PrimaryButton
import com.bazzar.android.presentation.destinations.MainScreenDestination
import com.bazzar.android.presentation.theme.COLOR_BLUE
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun OnBoarding(
    navigator: DestinationsNavigator,
) {
    val items = OnBoardingItems.getData()
    val pageState = rememberPagerState()

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            count = items.size,
            state = pageState,
            modifier = Modifier
                .fillMaxHeight(0.8f)
                .fillMaxWidth()
        ) { page ->
            OnBoardingItem(items = items[page])
        }

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(12.dp)
        ) {
            Indicators(items.size, pageState.currentPage)
        }

        if (pageState.currentPage == items.size - 1) {
            PrimaryButton(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                text = stringResource(R.string.onboarding_start),
                onClick = {
                    navigator.navigate(MainScreenDestination())
                }
            )
        }

        if (pageState.currentPage < items.size - 1) {
            Text(
                color = COLOR_BLUE,
                fontWeight = FontWeight.Bold,
                text = stringResource(R.string.onboarding_skip),
                modifier = Modifier
                    .padding(end = 16.dp, top = 16.dp)
                    .align(Alignment.End)
                    .clickable {
                        navigator.navigate(MainScreenDestination())
                    },
            )
        }
    }
}