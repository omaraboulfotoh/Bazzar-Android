package com.bazzar.android.presentation.categoryScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
fun Search(
    searchClicked: Boolean,
    onSearchClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .height(35.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(colorResource(id = if (searchClicked) R.color.white_smoke else R.color.white))
            .border(
                1.dp,
                colorResource(id = if (searchClicked) R.color.prussian_blue else R.color.white_smoke),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        IconButton(
            onClick = { onSearchClick(searchClicked) },
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .weight(1f), contentAlignment = Alignment.Center
                ) {
                    if (!searchClicked) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 104.dp),
                            painter = painterResource(id = R.drawable.search_icon),
                            contentDescription = null,
                            tint = colorResource(id = R.color.prussian_blue)
                        )
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 128.dp),
                            text = stringResource(id = R.string.brand_search_title),
                            style = MaterialTheme.typography.overline.copy(
                                fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                                color = colorResource(id = R.color.prussian_blue).copy(alpha = 0.4f),
                            ),
                        )
                    } else {
                        var searchText by remember { mutableStateOf("") }
                        TextField(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .width(280.dp)
                                .height(50.dp)
                                .padding(start = 33.dp),
                            value = searchText,
                            onValueChange = { inputText -> searchText = inputText },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = colorResource(id = if (!searchClicked) R.color.white else R.color.white_smoke)
                            ),
                            textStyle = MaterialTheme.typography.overline.copy(
                                fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                                color = colorResource(id = R.color.deep_sky_blue),
                            )
                        )
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 284.dp),
                            text = stringResource(id = R.string.brand_search_cancel),
                            style = MaterialTheme.typography.overline.copy(
                                fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                                color = colorResource(id = R.color.deep_sky_blue),
                            ),
                        )
                    }
                }
            }
        }
    }
}