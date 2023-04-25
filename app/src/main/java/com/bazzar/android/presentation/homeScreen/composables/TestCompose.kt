package com.bazzar.android.presentation.homeScreen.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.HeaderTextWithViewAll

@Preview
@Composable
fun CategoryyGroup() {
    HeaderTextWithViewAll(text = stringResource(id = R.string.home_featured_category))
    Box(
        Modifier
            .padding(top = 16.dp)
            .padding(start = 16.dp)
            .height(216.dp)
            .fillMaxWidth()
            .background(Color.Green)
    ) {
        Card(
            modifier = Modifier
                .size(168.dp)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
            elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
            content = {}
        )
        Image(
            painter = painterResource(R.drawable.first_bazzar),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(120.dp)
                .fillMaxSize()
                .background(Color.Red),
            contentScale = ContentScale.Crop
        )

    }
}

@Composable
fun Search(
    isCategory: Boolean = false,
    searchClicked: Boolean = true,
    onSearchClick: (Boolean) -> Unit = { "" }
) {
    if (!isCategory) {
        Box(
            modifier = Modifier
                .width(343.dp)
                .height(35.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
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
                            .weight(1f),
                        contentAlignment = Alignment.Center
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
                            androidx.compose.material.Text(
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
                                    .width(50.dp)
                                    .height(50.dp),
                                value = searchText,
                                onValueChange = { inputText -> searchText = inputText })
                            androidx.compose.material.Text(
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
}

@Composable
fun FootterTabBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(115.dp)
    ) {
        Box(
            modifier = Modifier
                .height(86.dp)
                .background(Color.White)
                .align(Alignment.BottomCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 47.dp)
                    .height(86.dp)
                    .align(Alignment.BottomCenter),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.footer_home),
                    style = MaterialTheme.typography.caption.copy(
                        color = colorResource(id = R.color.black).copy(
                            alpha = 0.85f
                        )
                    )
                )
                Icon(painterResource(id = R.drawable.ic_category), contentDescription = "category")
                Icon(painterResource(id = R.drawable.ic_bazzar), contentDescription = "bazzar")
                Icon(painterResource(id = R.drawable.ic_cart), contentDescription = "cart")
                Icon(
                    painterResource(id = R.drawable.ic_profile),
                    contentDescription = "profile",
                    modifier = Modifier.padding(end = 40.dp)
                )
            }

        }

        Box(
            modifier = Modifier
                .offset(40.dp)
                .align(Alignment.TopStart)
                .size(52.dp)
                .clip(RoundedCornerShape(26.dp))
                .border(
                    BorderStroke(0.5.dp, colorResource(id = R.color.deep_sky_blue))
                )
                .background(colorResource(id = R.color.prussian_blue)),

            contentAlignment = Alignment.Center
        ) {
            Icon(
                painterResource(id = R.drawable.ic_home),
                contentDescription = "home",
                tint = colorResource(id = R.color.deep_sky_blue),

                )
        }
    }
}

@Composable
fun Drawing() {

    Box(
        modifier = Modifier
            .height(500.dp)
            .width(500.dp)
            .background(Color.Gray),
        contentAlignment = Alignment.TopCenter

    ) {
        Spacer(modifier = Modifier.size(50.dp))
        Column(
            modifier = Modifier
//                .fillMaxSize()
                .height(200.dp)
                .width(300.dp)
                .background(Color.Green)
//                .align(Alignment.TopEnd),
//            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("ahmed")
            Spacer(Modifier.size(50.dp))
            Text("shafie")
        }

    }
}