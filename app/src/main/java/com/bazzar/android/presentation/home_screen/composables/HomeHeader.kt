package com.bazzar.android.presentation.home_screen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun HomeHeader(onSearchClicked: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(
                BazzarTheme.spacing.l
            ),
    ) {
        Image(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center),
            painter = painterResource(R.drawable.bazzars_home_title),
            contentDescription = "HomeScreenTitle",
        )
        Image(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterEnd)
                .clickable {
                    onSearchClicked()
                },
            painter = painterResource(R.drawable.search_icon),
            contentDescription = "searchIcon",
        )

    }

}
