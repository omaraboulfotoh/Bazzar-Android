package com.bazzar.android.presentation.composables

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.theme.Shapes
import com.bazzar.android.R

@Composable
fun RemoteImage(
    imageUrl: String?,
    description: String? = null,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    placeholder: Painter = painterResource(id = R.drawable.bazzars_home_title),
    contentScale: ContentScale = ContentScale.Crop,
    withShimmer: Boolean = false,
    background: Color = BazzarTheme.colors.transparentColor,
    alpha: Float = DefaultAlpha,
) {
    val contentScaleState = remember { mutableStateOf(contentScale) }
    val showShimmer = remember { mutableStateOf(true) }
    Card(modifier = modifier.background(background)) {
        AsyncImage(
            model = imageUrl,
            placeholder = if (withShimmer.not()) placeholder else null,
            contentDescription = description,
            modifier = if (withShimmer) {
                Modifier
                    .fillMaxSize()
                    .background(
                        shimmerBrush(targetValue = 1300f, showShimmer = showShimmer.value)
                    )
            } else Modifier.fillMaxSize(),
            alignment = alignment,
            contentScale = contentScaleState.value,
            alpha = alpha,
            error = placeholder,
            onSuccess = {
                showShimmer.value = false
            },
            onError = {
                showShimmer.value = false
                Log.e("RemoteImage", "${it.result.throwable}")
            },
        )
    }

}

@Composable
fun shimmerBrush(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            Color.LightGray.copy(alpha = 0.6f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.6f),
        )

        val transition = rememberInfiniteTransition()
        val translateAnimation = transition.animateFloat(
            initialValue = 0f, targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(800), repeatMode = RepeatMode.Reverse
            )
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent, Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}

@Composable
fun DashLine(
    modifier: Modifier = Modifier,
    height: Dp = BazzarTheme.spacing.xs,
    width: Dp = 112.dp,
    background: Color = Color.Black,
) {
    Spacer(
        modifier = modifier
            .height(height)
            .width(width)
            .background(background, Shapes.medium)
    )
}
