package com.bazzar.android.presentation.wishlist.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun PagerTabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Prepare text styles
    val selectedTextStyle = BazzarTheme.typography.body2Medium.copy(
        color = BazzarTheme.colors.black
    )
    val normalTextStyle =  BazzarTheme.typography.body2Medium.copy(
        color = BazzarTheme.colors.textGray
    )

    // Tab text container to center it
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        // Tab text
        Text(
            text = title,
            style = if (isSelected) selectedTextStyle else normalTextStyle,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PagerTabItemPreview() {
    PagerTabItem(
        title = "Open",
        isSelected = false,
        onClick = {}
    )
}
