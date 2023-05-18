package com.bazzar.android.presentation.wishlist.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun PagerTabItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Prepare text styles
    val selectedTextStyle = BazzarTheme.typography.body2Bold.copy(
        color = BazzarTheme.colors.black
    )
    val normalTextStyle = BazzarTheme.typography.body2SemiBold.copy(
        color = BazzarTheme.colors.textGray
    )

    val density = LocalDensity.current
    var textWidth by remember {
        mutableStateOf(0)
    }

    // Tab text container to center it
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        // Tab text
        Text(
            modifier = Modifier
                .wrapContentSize()
                .onSizeChanged {
                    textWidth = it.width
                }
                .align(Alignment.TopCenter),
            text = title,
            style = if (isSelected) selectedTextStyle else normalTextStyle,
            textAlign = TextAlign.Center
        )
        if (isSelected)
            Divider(
                modifier = Modifier
                    .width(with(density) { textWidth.toDp() })
                    .align(Alignment.BottomCenter),
                color = BazzarTheme.colors.black,
                thickness = 2.dp
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
