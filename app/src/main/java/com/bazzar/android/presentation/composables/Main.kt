package com.bazzar.android.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bazzar.android.common.noRippleClickable
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.R

@Composable
fun BazzarAppBar(
    title: String = String(),
    modifier: Modifier = Modifier,
    navigationIcon: ImageVector? = ImageVector.vectorResource(id = R.drawable.ic_back),
    navigationIconTint: Color = BazzarTheme.colors.primaryButtonColor,
    actions: (@Composable () -> Unit)? = null,
    backgroundColor: Color = Color.Transparent,
    isUpperCase: Boolean = true,
    onNavigationClick: () -> Unit = {},
) {
    Box(
        modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(top = BazzarTheme.spacing.primaryPadding)
            .padding(horizontal = BazzarTheme.spacing.primaryPadding)
    ) {

        navigationIcon?.let {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .defaultMinSize(minWidth = 50.dp, minHeight = 50.dp)
                    .noRippleClickable(onClick = onNavigationClick)
            ) {
                Image(
                    imageVector = navigationIcon,
                    alignment = Alignment.Center,
                    contentDescription = "navigate",
                    modifier = Modifier.align(Alignment.CenterStart),
                    colorFilter = ColorFilter.tint(navigationIconTint)
                )
            }
        }

        Title(
            modifier = Modifier.align(Alignment.Center),
            text = title,
            isUpperCase = true,
            color = navigationIconTint,
            style = BazzarTheme.typography.subtitle1Bold,
            textAlign = TextAlign.Center
        )

        actions?.let { actions ->
            Box(
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                actions.invoke()
            }
        }
    }
}

@Composable
fun MbcAppBarWithClose(
    title: String = String(),
    modifier: Modifier = Modifier,
    navigationIcon: ImageVector? = Icons.Filled.ArrowBackIos,
    navigationIconTint: Color = Color.White,
    gradient: Brush = BazzarTheme.colors.primaryGradient,
    onNavigationClick: () -> Unit = {},
    onCloseClick: () -> Unit = {},
) {
    Row(
        modifier
            .background(gradient)
            .padding(BazzarTheme.spacing.l)
    ) {

        navigationIcon?.let {
            Image(
                imageVector = navigationIcon,
                contentDescription = "navigate",
                modifier = Modifier.clickable(onClick = onNavigationClick),
                colorFilter = ColorFilter.tint(navigationIconTint)
            )
        }

        Title(
            text = title,
            color = BazzarTheme.colors.primaryButtonColor,
            modifier = Modifier.weight(1f)
        )

        Image(
            imageVector = Icons.Filled.Close,
            contentDescription = "close",
            modifier = Modifier.clickable(onClick = onCloseClick),
            colorFilter = ColorFilter.tint(navigationIconTint)
        )
    }
}


@Composable
fun EmptyMbcAppBar(title: String = String()) = BazzarAppBar(title = title, navigationIcon = null)

@Composable
fun RowScope.MbcAppBarCloseAction(
    onClose: () -> Unit,
) {
    Image(
        imageVector = Icons.Filled.Close,
        contentDescription = "Close",
        modifier = Modifier.clickable(onClick = onClose)
    )
}
