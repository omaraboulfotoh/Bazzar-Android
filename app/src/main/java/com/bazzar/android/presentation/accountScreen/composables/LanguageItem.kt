package com.bazzar.android.presentation.accountScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun LanguageItem(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(colorResource(id = R.color.white)),
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = BazzarTheme.spacing.l),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
        ) {
            Icon(
                modifier = Modifier.wrapContentSize(),
                imageVector = ImageVector.vectorResource(R.drawable.icon_awesome_flag),
                contentDescription = null,
                tint = BazzarTheme.colors.primaryButtonColor
            )
            Text(
                modifier = Modifier.wrapContentSize(),
                text = stringResource(id = R.string.language),
                style = MaterialTheme.typography.subtitle2.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    color = colorResource(id = R.color.prussian_blue)
                )
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = BazzarTheme.spacing.m),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
        ) {
            Text(
                text = stringResource(id = R.string.english),
                style = MaterialTheme.typography.subtitle2.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    color = colorResource(id = R.color.deep_sky_blue)
                )
            )
            Text(
                text = stringResource(id = R.string.arabic),
                style = MaterialTheme.typography.subtitle2.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    color = colorResource(id = R.color.Gray59)
                ),
                modifier = Modifier.wrapContentSize()
            )

        }
    }

}