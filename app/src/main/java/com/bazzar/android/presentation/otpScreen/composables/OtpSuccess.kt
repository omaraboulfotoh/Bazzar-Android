package com.bazzar.android.presentation.otpScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bazzar.android.R

@Composable
fun OtpSuccess() {
    Column(
        modifier = Modifier
            .width(343.dp)
            .height(105.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(colorResource(id = R.color.prussian_blue))
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_right_mark), null)
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(id = R.string.account_confirmed),
            style = MaterialTheme.typography.subtitle2.copy(
                colorResource(id = R.color.white),
                fontFamily = FontFamily(Font(R.font.montserrat_regular))
            )
        )
    }
}