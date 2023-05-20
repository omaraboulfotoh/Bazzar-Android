package com.bazzar.android.presentation.ordersHistory.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.ordersHistory.OrdersHistoryContract
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun OrdersHistoryScreenContent(
    modifier: Modifier = Modifier,
    state: OrdersHistoryContract.State,
    onSendEvent: (OrdersHistoryContract.Event) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor)
            .padding(bottom = BottomNavigationHeight),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
    ) {
        BazzarAppBar(
            title = stringResource(id = R.string.history_list),
            onNavigationClick = { onSendEvent.invoke(OrdersHistoryContract.Event.OnBackIconClicked) }
        )
        TimeCategory(
            timeCategoryList = state.timeCategoryList.orEmpty(),
            selectedTimeCategoryIndex = state.selectedTimeCategoryIndex,
            onTimeCategoryClicked = {
                onSendEvent.invoke(
                    OrdersHistoryContract.Event.OnTimeCategoryClicked(
                        it
                    )
                )
            }
        )
        if (state.showEmptyView) {
            Box(modifier = Modifier.weight(1f)) {
                Image(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.Center),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_empty_view),
                    contentDescription = "ic_empty_view"
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(BazzarTheme.spacing.m),
                verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
            ) {
                itemsIndexed(state.orderList.orEmpty()) { _, item ->
                    OrderHistoryItem(orderHistory = item)
                }
            }
        }
    }
}