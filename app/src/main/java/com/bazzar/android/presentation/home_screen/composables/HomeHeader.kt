package com.bazzar.android.presentation.home_screen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R

@Composable
fun HomeHeader() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(
                top = 32.dp, start = 127.dp
            ),
        horizontalArrangement = Arrangement.Start
    ) {
        Image(

            painter = painterResource(R.drawable.bazzars_home_title),
            contentDescription = "HomeScreenTitle",
        )
        Spacer(modifier = Modifier
            .width(85.dp)
            .clickable {

            })
        Image(
            painter = painterResource(R.drawable.search_icon),
            contentDescription = "searchIcon",
        )

    }

}
