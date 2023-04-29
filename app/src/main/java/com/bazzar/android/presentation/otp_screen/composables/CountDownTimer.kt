package com.bazzar.android.presentation.otp_screen.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun CountdownTimer(durationInSeconds: Int) {
    var timeLeft = remember { mutableStateOf(durationInSeconds) }.value
    var isRunning = remember { mutableStateOf(false) }.value

    LaunchedEffect(isRunning) {
        while (isRunning && timeLeft >  0) {
            delay(1000)
            timeLeft--
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "${timeLeft / 60}:${(timeLeft % 60).toString().padStart(2, '0')}",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { isRunning = !isRunning },
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text(text = if (isRunning) "Pause" else "Start")
            }
            Button(
                onClick = { timeLeft = durationInSeconds },
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Text(text = "Reset")
            }
        }
    }
}