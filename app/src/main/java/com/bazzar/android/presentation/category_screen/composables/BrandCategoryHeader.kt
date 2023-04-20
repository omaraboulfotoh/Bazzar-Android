package com.bazzar.android.presentation.category_screen.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun BrandCategoryHeader(showBack: Boolean) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = BazzarTheme.spacing.m),
    ) {

        // show back button
        if (showBack) {
            IconButton(
                onClick = {},
                modifier = Modifier.align(alignment = Alignment.CenterStart)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back), contentDescription = "back"
                )
            }
        } else {
            IconButton(
                onClick = {
                }, modifier = Modifier.align(alignment = Alignment.CenterEnd)
            ) {
                Icon(
                    painter = painterResource(R.drawable.search_icon), contentDescription = "Search"
                )
            }
        }

        // handle the title view
        Text(
            text = stringResource(
                id = if (showBack.not()) R.string.category_category_title else R.string.category_brands_title
            ), style = MaterialTheme.typography.subtitle1.copy(
                fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                color = colorResource(id = R.color.prussian_blue),
            ), modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize()
        )

    }
}