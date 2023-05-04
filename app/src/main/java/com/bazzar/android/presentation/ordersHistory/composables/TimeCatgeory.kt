package com.bazzar.android.presentation.ordersHistory.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun TimeCategory(modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs),
        contentPadding = PaddingValues(horizontal = BazzarTheme.spacing.xs)
    ) {
        itemsIndexed(listOf("All", "Last Weak", "Last Month")) { index, s ->
            Box(
                modifier = Modifier
                    .background(BazzarTheme.colors.white, shape = RoundedCornerShape(8.dp))
                    .padding(vertical = 8.dp, horizontal = 16.dp),
            ) {
                Text(
                    text = s,
                    color = if (index == 0) BazzarTheme.colors.black else BazzarTheme.colors.indicatorGrey,
                    style = BazzarTheme.typography.captionMedium
                )
            }
        }
    }
}