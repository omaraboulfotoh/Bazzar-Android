package com.bazzar.android.presentation.productsList.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
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
fun ProductHeader(productScreenTitle: String, modifier: Modifier) {
    Row(
        modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_back), contentDescription = "back",
            modifier = Modifier
                .padding(start = 16.dp)
                .clickable { }
        )
        /*
                Image(
                    painter = painterResource(R.drawable.product_icon),
                    contentDescription = "Share",
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable { }
                )
        */
        Box(
            Modifier.weight(1f)
        ) {
            Text(
                text = productScreenTitle, style = MaterialTheme.typography.subtitle1.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                    color = colorResource(id = R.color.prussian_blue),
                ), modifier = Modifier.align(Alignment.Center)
            )
        }
        Icon(
            painter = painterResource(R.drawable.ic_share),
            contentDescription = "Share",
            tint = colorResource(id = R.color.prussian_blue),
            modifier = Modifier
                .padding(start = 16.dp)
                .clickable { }
        )
    }
}
