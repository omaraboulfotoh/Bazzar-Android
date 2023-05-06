package com.bazzar.android.presentation.otpScreen.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bazzar.android.R

@Composable
fun HeaderTitleBack(modifier:Modifier, title:String) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            null,
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.subtitle2.copy(
                color = colorResource(id = R.color.prussian_blue),
                fontFamily = FontFamily(Font(R.font.montserrat_bold)),

                ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}