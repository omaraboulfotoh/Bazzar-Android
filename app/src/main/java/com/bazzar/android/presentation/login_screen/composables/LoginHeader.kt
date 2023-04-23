package com.bazzar.android.presentation.login_screen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R

@Composable
fun LoginHeader(modifier: Modifier, onAction: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding()
    ) {

        Icon(
            painter = painterResource(id = R.drawable.icon_ionic_ios_close_circle_outline),
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.TopStart)
                .clickable {
                    onAction()
                }
        )
        Row(modifier = Modifier.padding(start = 41.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.bazzars_home_title),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f),
            )
        }

    }
}