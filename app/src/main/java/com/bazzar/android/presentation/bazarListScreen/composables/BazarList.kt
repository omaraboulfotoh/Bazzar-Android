package com.bazzar.android.presentation.bazarListScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.android.model.home.BazaarModel
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun BazarList(
    modifier: Modifier = Modifier,
    bazarList: List<BazaarModel> = emptyList(),
    onBazarItemClick: (bazar: BazaarModel) -> Unit,
    onBazarItemFavClick: (bazar: BazaarModel) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(BazzarTheme.spacing.xs),
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m),
    ) {
        itemsIndexed(bazarList) { index, item ->
            BazarListItem(item = item,
                onClick = { onBazarItemClick.invoke(item) },
                onFavClicked = { onBazarItemFavClick(item) })
            if (index == bazarList.lastIndex) {
                Spacer(modifier = Modifier.height(BazzarTheme.spacing.m))
            }
        }
    }
}