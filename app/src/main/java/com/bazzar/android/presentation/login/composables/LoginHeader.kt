package com.bazzar.android.presentation.login.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun LoginHeader(modifier: Modifier, onAction: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(BazzarTheme.colors.backgroundColor)
    ) {

        Image(
            painter = painterResource(id = R.drawable.icon_ionic_ios_close_circle_outline),
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = BazzarTheme.spacing.m)
                .clickable { onAction() })

        Image(
            painter = painterResource(id = R.drawable.bazzars_home_title),
            contentDescription = "",
            modifier = Modifier.align(Alignment.Center)
        )

    }
}