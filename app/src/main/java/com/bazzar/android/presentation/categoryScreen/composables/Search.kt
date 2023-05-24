package com.bazzar.android.presentation.categoryScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun Search(
    modifier: Modifier = Modifier,
    isSearchOpen: Boolean,
    searchTerm: String,
    onSearchTermChanged: (String) -> Unit,
    onSearchClick: (Boolean) -> Unit,
    onCancelSearchClicked: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .background(colorResource(id = R.color.white))
            .border(
                1.dp,
                colorResource(id = R.color.white_smoke),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        IconButton(
            onClick = { onSearchClick(isSearchOpen.not()) },
        ) {
            Row(
                modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .weight(1f), contentAlignment = Alignment.Center
                ) {
                    if (!isSearchOpen) {
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
                                fontFamily = FontFamily(Font(R.font.siwa_heavy)),
                                color = colorResource(id = R.color.prussian_blue).copy(alpha = 0.4f),
                            ),
                        )
                    } else {
                        TextField(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .defaultMinSize(minHeight = 36.dp)
                                .padding(start = BazzarTheme.spacing.m, end = 40.dp),
                            value = searchTerm,
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            onValueChange = onSearchTermChanged,
                            textStyle = BazzarTheme.typography.overlineBold.copy(color = BazzarTheme.colors.dodgerBlue)
                        )
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = BazzarTheme.spacing.m)
                                .clickable { onCancelSearchClicked() },
                            text = stringResource(id = R.string.brand_search_cancel),
                            style = MaterialTheme.typography.overline.copy(
                                fontFamily = FontFamily(Font(R.font.siwa_bold)),
                                color = colorResource(id = R.color.deep_sky_blue),
                            ),
                        )
                    }
                }
            }
        }
    }
}
