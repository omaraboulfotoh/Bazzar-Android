package com.bazzar.android.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.theme.Shapes

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    background: Color = BazzarTheme.colors.primaryButtonColor,
    disabledBackground: Color = BazzarTheme.colors.primaryButtonDisableColor,
    textColor: Color = BazzarTheme.colors.primaryButtonTextColor,
    textColorDisabled: Color = BazzarTheme.colors.primaryButtonTextColorDisabled,
    enabled: Boolean = true,
    icon: ImageVector? = null,
) {
    Button(
        onClick = onClick,
        shape = Shapes.medium,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = background,
            disabledBackgroundColor = disabledBackground
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        ),
        modifier = modifier.defaultMinSize(minHeight = 46.dp),
        enabled = enabled
    ) {
        MessageBodyBold(
            text = text.uppercase(),
            color = if (enabled) textColor else textColorDisabled,
            style = BazzarTheme.typography.body2Bold
        )
        icon?.let {
            Image(imageVector = icon, contentDescription = "")
            Spacer(modifier = Modifier.width(BazzarTheme.spacing.spacerMini))
        }

    }
}

@Composable
fun PrimaryOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    enabled: Boolean = true,
    stroke: Color = BazzarTheme.colors.white,
    color: Color = BazzarTheme.colors.secondaryText,
    background: Color = BazzarTheme.colors.primaryButtonColor,
    icon: ImageVector? = null,
    style: TextStyle = BazzarTheme.typography.body2Bold,
) {

    OutlinedButton(
        modifier = modifier.defaultMinSize(minHeight = 46.dp),
        onClick = onClick,
        shape = Shapes.medium,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = background,
        ),
        border = BorderStroke(1.dp, stroke),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        )
    ) {

        icon?.let {
            Image(imageVector = icon, contentDescription = "")
            Spacer(modifier = Modifier.width(BazzarTheme.spacing.spacerMini))
        }

        MessageBody(
            text = text,
            style = style,
            color = color
        )
    }
}
