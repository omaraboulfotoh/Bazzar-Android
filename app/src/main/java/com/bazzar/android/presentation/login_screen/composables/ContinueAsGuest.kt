package com.bazzar.android.presentation.login_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bazzar.android.R

@Composable
fun ContinueAsGuest(modifier: Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.height(76.dp)) {
        Button(
            modifier = modifier
                .width(343.dp)
                .height(65.dp)
                .clip(RoundedCornerShape(32.5.dp))
                .border(
                    1.dp,
                    colorResource(id = R.color.Cruel_Jewel),
                    shape = RoundedCornerShape(32.5.dp)
                )
                .align(Alignment.Start),
            onClick = { /*TODO*/ }) {
            Text(
                text = stringResource(id = R.string.Create_new_account),
                style = MaterialTheme.typography.subtitle2.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    color = colorResource(id = R.color.Cruel_Jewel)
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .height(5.dp)
                .align(Alignment.End)
        )
    }
}