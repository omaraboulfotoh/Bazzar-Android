package com.bazzar.android.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.BazzarTheme


@Composable
fun ErrorView(modifier: Modifier = Modifier, onTryAgainClicked: () -> Unit) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Center)
                .padding(BazzarTheme.spacing.m),
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_error_icon),
                contentDescription = "ic_error_icon"
            )
            Title(
                text = stringResource(id = R.string.error_text),
                color = BazzarTheme.colors.primaryButtonColor,
                style = BazzarTheme.typography.body2Bold
            )
        }

        // try again button
        Box(Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
                    .padding(all = BazzarTheme.spacing.m)
                    .clip(RoundedCornerShape(32.5.dp))
                    .background(colorResource(id = R.color.prussian_blue))
                    .align(Alignment.BottomCenter)
                    .clickable {
                        onTryAgainClicked()
                    }
            ) {
                Text(
                    text = stringResource(id = R.string.try_again),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_bold))
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

    }
}