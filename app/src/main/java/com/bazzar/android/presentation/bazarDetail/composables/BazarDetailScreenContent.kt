package com.bazzar.android.presentation.bazarDetail.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.bazzar.android.presentation.bazarDetail.BazarDetailContract
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.ProductItem
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.productsList.composables.BrandImage
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.theme.Shapes

@Composable
fun BazarDetailScreenContent(
    state: BazarDetailContract.State, onSendEvent: (BazarDetailContract.Event) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor)
            .padding(bottom = BottomNavigationHeight),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
    ) {
        BazzarAppBar(
            title = state.productScreenTitle,
            onNavigationClick = { onSendEvent(BazarDetailContract.Event.OnBackIconClicked) }
        )
        // show brands
        if (state.brand != null) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(horizontal = BazzarTheme.spacing.m)
            ) {
                BrandImage(
                    state.brand,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(Shapes.large)
                )
            }
        }

        // list items
        LazyVerticalGrid(
            modifier = Modifier.weight(1f),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(BazzarTheme.spacing.m),
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs),
            horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs),
        ) {
            itemsIndexed(state.productList.orEmpty()) { index, item ->
                ProductItem(item) {
                    onSendEvent(BazarDetailContract.Event.OnProductClicked(index))
                }
            }
        }
    }
}









