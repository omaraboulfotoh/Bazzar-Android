package com.bazzar.android.presentation.bazarListScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.android.model.home.Bazar
import com.bazzar.android.presentation.composables.RemoteImage
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun BazarListItem(
    modifier: Modifier = Modifier,
    item: Bazar,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        Column(modifier = Modifier
            .background(
                color = BazzarTheme.colors.primaryButtonColor,
                shape = CircleShape.copy(
                    bottomStart = CornerSize(16.dp),
                    bottomEnd = CornerSize(16.dp)
                )
            )
            .clickable { onClick.invoke() }
            .padding(6.dp)
            .padding(bottom = BazzarTheme.spacing.xxl),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            RemoteImage(
                modifier = Modifier
                    .size(126.dp)
                    .clip(CircleShape),
                imageUrl = item.imagePath
            )
            Text(
                modifier = Modifier.padding(top = BazzarTheme.spacing.xs),
                text = item.name ?: "",
                style = BazzarTheme.typography.body2Medium,
                color = BazzarTheme.colors.white,
            )
        }
    }
}