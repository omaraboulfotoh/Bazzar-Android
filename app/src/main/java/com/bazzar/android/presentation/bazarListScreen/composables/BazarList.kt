package com.bazzar.android.presentation.bazarListScreen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
        contentPadding = PaddingValues(BazzarTheme.spacing.m),
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m),
    ) {
        items(bazarList) { item ->
            BazarListItem(item = item,
                onClick = { onBazarItemClick.invoke(item) },
                onFavClicked = { onBazarItemFavClick(item) })
        }
    }
}