package com.bazzar.android.presentation.productDetail.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.common.advancedShadow
import com.bazzar.android.presentation.theme.BazzarTheme

@Preview
@Composable
fun TalkToUs(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
) {
    var isExpanded by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .height(64.dp)
            .advancedShadow(
                color = if (isExpanded) BazzarTheme.colors.dodgerBlue else BazzarTheme.colors.borderColor,
                cornersRadius = if (isExpanded) 24.dp else 32.dp,
                shadowBlurRadius = 15.dp,
                alpha = 0.5f,
                offsetX = 5.dp,
                offsetY = 5.dp,
            )
            .border(
                width = if (isExpanded) 1.dp else 0.dp,
                color = if (isExpanded) BazzarTheme.colors.dodgerBlue else Color.Transparent,
                shape = if (isExpanded) RoundedCornerShape(24.dp) else RoundedCornerShape(50)
            )
            .background(
                color = BazzarTheme.colors.white,
                shape = if (isExpanded) RoundedCornerShape(24.dp) else RoundedCornerShape(50)
            )
            .clickable {
                if (isExpanded) {
                    isExpanded = false
                    onClick.invoke()
                } else {
                    isExpanded = true
                }
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AnimatedVisibility(visible = isExpanded) {
            Text(
                modifier = Modifier.padding(start = BazzarTheme.spacing.m),
                text = stringResource(R.string.talk_to_us),
                style = BazzarTheme.typography.body2Bold,
                color = BazzarTheme.colors.dodgerBlue,
            )
        }
        Icon(
            modifier = Modifier.size(64.dp),
            painter = painterResource(id = R.drawable.ic_talk_to_us),
            tint = Color.Unspecified,
            contentDescription = "",
        )
    }
}