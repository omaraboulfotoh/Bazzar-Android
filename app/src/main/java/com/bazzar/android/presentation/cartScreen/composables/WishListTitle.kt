package com.bazzar.android.presentation.cartScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bazzar.android.R

@Composable
fun WishListTitle() {
    Box(
        Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .height(44.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.add_from_your_wish_list),
            style = MaterialTheme.typography.subtitle2.copy(
                fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                color = colorResource(id = R.color.prussian_blue),
            )
        )
    }
}
