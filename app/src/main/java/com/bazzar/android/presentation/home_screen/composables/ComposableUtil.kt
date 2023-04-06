package com.bazzar.android.presentation.home_screen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bazzar.android.R


@Composable
fun Indicator(selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(if (selected) colorResource(id = R.color.prussian_blue) else Color.White)
            .clickable(onClick = onClick)
    )
    Spacer(modifier = Modifier.width(2.dp))
}

@Composable
fun SemiCircleImageView(image: Int, text: String) {
    Box(
        modifier = Modifier
            .width(136.dp)
            .height(215.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 68.dp,
                    topEnd = 68.dp,
                    bottomStart = 22.dp,
                    bottomEnd = 22.dp
                )
            )
            .background(colorResource(id = R.color.prussian_blue))
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Unspecified, CircleShape)
                .padding(all = 5.dp)
                .size(126.dp)
                .align(Alignment.TopCenter),
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.Crop, modifier = Modifier
                    .width(126.dp)
                    .height(126.dp)
                    .clip(CircleShape)
            )
        }
        Text(
            text = text,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 55.dp)
                .align(Alignment.BottomEnd),
            style = MaterialTheme.typography.subtitle2.copy(
                fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                color = colorResource(id = R.color.white)
            )
        )
    }
}

@Composable
fun HeaderTextWithViewAll(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1.copy(
                fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                color = colorResource(id = R.color.prussian_blue),
            )
        )
        Text(
            text = stringResource(id = R.string.home_screen_view_all),
            style = MaterialTheme.typography.caption.copy(
                fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                color = colorResource(id = R.color.deep_sky_blue)
            )
        )
    }
}

@Composable
fun CustomLazyRow(
    imageList: List<Int>,
    textList: List<String>,
    customIV: @Composable (image: Int, text: String) -> Unit,
    topPadding: Dp,
    spaceBetweenItems: Dp
) {
    androidx.compose.foundation.lazy.LazyRow(
        modifier = Modifier
            .padding(top=topPadding)
            .wrapContentWidth(),
        horizontalArrangement = Arrangement.spacedBy(spaceBetweenItems),
        contentPadding = PaddingValues(horizontal = spaceBetweenItems)
    ) {
        val dataList = imageList.zip(textList)
        items(dataList) { (image, text) ->
            customIV(image, text)
        }
    }
}

@Preview
@Composable
fun FooterTabBar(selectedMenu: MenuBarItem = MenuBarItem.Profile) {
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
                    .padding(horizontal = 40.dp)
                    .height(86.dp)
                    .align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (selectedMenu != MenuBarItem.Home)
                    Icon(painterResource(id = R.drawable.ic_home_menu), contentDescription = "home")
                else Text(
                    text = stringResource(id = R.string.footer_home), modifier = Modifier,
                    style = MaterialTheme.typography.caption.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                        color = colorResource(id = R.color.black).copy(
                            alpha = 0.15f,
                        )
                    )
                )

                if (selectedMenu != MenuBarItem.Category)
                    Icon(
                        painterResource(id = R.drawable.ic_category),
                        contentDescription = "category"
                    )
                else Text(
                    text = stringResource(id = R.string.category_category_title),
                    modifier = Modifier,
                    style = MaterialTheme.typography.caption.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                        color = colorResource(id = R.color.black).copy(
                            alpha = 0.15f,
                        )
                    )
                )
                if (selectedMenu != MenuBarItem.Bazar)
                    Icon(painterResource(id = R.drawable.ic_bazzar), contentDescription = "bazzar")
                else Text(
                    text = stringResource(id = R.string.footer_menu_bazzar), modifier = Modifier,
                    style = MaterialTheme.typography.caption.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                        color = colorResource(id = R.color.black).copy(
                            alpha = 0.15f,
                        )
                    )
                )
                if (selectedMenu != MenuBarItem.Cart)
                    Icon(painterResource(id = R.drawable.ic_cart), contentDescription = "cart")
                else Text(
                    text = stringResource(id = R.string.footer_menu_cart), modifier = Modifier,
                    style = MaterialTheme.typography.caption.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                        color = colorResource(id = R.color.black).copy(
                            alpha = 0.15f,
                        )
                    )
                )
                if (selectedMenu != MenuBarItem.Profile)
                    Icon(
                        painterResource(id = R.drawable.ic_profile),
                        contentDescription = "profile",
//                        modifier = Modifier.padding(end = 40.dp)
                    )
                else Text(
                    text = stringResource(id = R.string.footer_menu_profile), modifier = Modifier,
                    style = MaterialTheme.typography.caption.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                        color = colorResource(id = R.color.black).copy(
                            alpha = 0.15f,
                        )
                    )
                )

            }

        }
        // Update the offset of the selected icon's Box to align with the corresponding text
        val selectedOffset = when (selectedMenu) {
            MenuBarItem.Home -> 0.dp
            MenuBarItem.Category -> 1.dp
            MenuBarItem.Bazar -> 2.dp
            MenuBarItem.Cart -> 3.dp
            MenuBarItem.Profile -> 4.dp
        }

        Box(
            modifier = Modifier
                .offset(x = selectedOffset.times(80))
                .align(Alignment.TopStart)
                .size(52.dp)
                .clip(RoundedCornerShape(26.dp))
                .background(colorResource(id = R.color.prussian_blue)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painterResource(
                    id = when (selectedMenu) {
                        MenuBarItem.Home -> R.drawable.ic_home
                        MenuBarItem.Category -> R.drawable.ic_category
                        MenuBarItem.Profile -> R.drawable.ic_profile
                        MenuBarItem.Cart -> R.drawable.ic_cart
                        MenuBarItem.Bazar -> R.drawable.ic_bazzar
                    }
                ),
                contentDescription = "menus",
                tint = colorResource(id = R.color.deep_sky_blue),

                )
        }
    }
}

enum class MenuBarItem {
    Home,
    Category,
    Cart,
    Profile,
    Bazar,
}
