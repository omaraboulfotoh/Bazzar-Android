package com.bazzar.android.presentation.login.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun ContinueAsGuest(modifier: Modifier, onContinueAsGuestClicked: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.height(76.dp)
    ) {
        Button(
            modifier = modifier
                .fillMaxWidth()
                .height(65.dp)
                .clip(RoundedCornerShape(32.5.dp))
                .border(
                    1.dp,
                    colorResource(id = R.color.Cruel_Jewel),
                    shape = RoundedCornerShape(32.5.dp)
                )
                .align(Alignment.Start),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = BazzarTheme.colors.backgroundColor,
                disabledBackgroundColor = BazzarTheme.colors.backgroundColor,
            ),
            onClick = { onContinueAsGuestClicked() }) {
            Text(
                text = stringResource(id = R.string.continue_as_guest),
                style = MaterialTheme.typography.subtitle2.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    color = colorResource(id = R.color.Cruel_Jewel)
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}