package com.bazzar.android.presentation.ordersHistory.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun OrdersHistoryScreenContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor)
            .padding(bottom = BottomNavigationHeight),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
    ) {
        BazzarAppBar(title = stringResource(id = R.string.history_list), onNavigationClick = { })
        TimeCategory()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(BazzarTheme.spacing.m),
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
        ) {
            itemsIndexed(listOf(1, 2)) { _, _ ->
                OrderHistoryItem()
            }
        }
    }
}