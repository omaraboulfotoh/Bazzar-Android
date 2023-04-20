package com.bazzar.android.presentation.login_screen.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bazzar.android.R

@Composable
fun CreateNewAccount(modifier:Modifier) {
    Button(
        modifier = modifier
            .width(343.dp)
            .height(65.dp)
            .clip(RoundedCornerShape(32.5.dp))
            .border(
                1.dp,
                colorResource(id = R.color.prussian_blue),
                shape = RoundedCornerShape(32.5.dp)
            ),
        onClick = { /*TODO*/ }) {
        Text(
            text = stringResource(id = R.string.Create_new_account),
            style = MaterialTheme.typography.subtitle2.copy(
                fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                color = colorResource(id = R.color.prussian_blue)
            ),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}