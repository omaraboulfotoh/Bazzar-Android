package com.bazzar.android.presentation.login_screen.composables

import androidx.compose.foundation.background
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
import com.bazzar.android.presentation.composables.PrimaryButton

@Composable
fun LoginButton(
    modifier: Modifier,
    isEnabled: Boolean = false,
    onSubmit: () -> Unit
) {

    // todo replace wirth buttons
    //    PrimaryButton(text =, onClick = { /*TODO*/ })

    Button(
        modifier = modifier
            .width(343.dp)
            .height(65.dp)
            .clip(RoundedCornerShape(32.5.dp))
            .background(colorResource(id = R.color.prussian_blue)),
        enabled = isEnabled,
        onClick = { onSubmit() }) {
        Text(
            text = stringResource(id = R.string.login),
            style = MaterialTheme.typography.subtitle2.copy(
                fontFamily = FontFamily(Font(R.font.montserrat_bold))
            ),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}