package com.bazzar.android.presentation.productDetail.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun SuccessAddedToCart(
    modifier: Modifier = Modifier,
    show: Boolean = false,
    onContinueShoppingClick: () -> Unit,
    onVisitCardClick: () -> Unit,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.success_animation))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    AnimatedVisibility(
        modifier = modifier,
        visible = show,
        enter = slideInVertically(
            initialOffsetY = { it }, animationSpec = tween(durationMillis = 1000)
        ) + fadeIn(initialAlpha = 0.5f),
        exit = slideOutVertically(
            targetOffsetY = { -it }, animationSpec = tween(durationMillis = 1000)
        ) + fadeOut(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
                .background(
                    color = BazzarTheme.colors.primaryButtonColor, shape = RoundedCornerShape(24.dp)
                )
                .padding(vertical = 16.dp, horizontal = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            LottieAnimation(
                modifier = Modifier.size(56.dp),
                composition = composition,
                progress = { progress },
            )

            Text(
                modifier = Modifier
                    .padding(top = BazzarTheme.spacing.m)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.product_added_to_cart),
                style = BazzarTheme.typography.body2Bold,
                fontSize = 13.sp,
                color = BazzarTheme.colors.white,
                textAlign = TextAlign.Center,
            )

            Row(
                modifier = Modifier.padding(top = 56.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp)
                        .border(
                            width = 1.dp,
                            color = BazzarTheme.colors.white,
                            shape = RoundedCornerShape(33.dp),
                        ), shape = RoundedCornerShape(33.dp), onClick = onContinueShoppingClick
                ) {
                    Text(
                        text = stringResource(id = R.string.continue_shopping),
                        style = BazzarTheme.typography.body2Bold,
                        color = BazzarTheme.colors.white,
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp)
                        .background(
                            color = BazzarTheme.colors.white, shape = RoundedCornerShape(33.dp)
                        )
                        .clickable { onVisitCardClick.invoke() },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = stringResource(id = R.string.visit_cart),
                        style = BazzarTheme.typography.body2Bold,
                        color = BazzarTheme.colors.primaryButtonColor,
                        textAlign = TextAlign.Center
                    )
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(bottom = 4.dp),
                        painter = painterResource(id = R.drawable.ic_cart_icon),
                        contentDescription = "",
                        tint = BazzarTheme.colors.primaryButtonColor,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSuccessAddedToCart() {
    SuccessAddedToCart(show = true, onVisitCardClick = { }, onContinueShoppingClick = { })
}