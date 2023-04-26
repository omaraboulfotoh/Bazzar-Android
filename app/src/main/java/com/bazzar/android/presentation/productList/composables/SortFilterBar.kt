package com.bazzar.android.presentation.productList.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.bazzar.android.R

@Composable
fun SortFilterBar(
    numberFilteredProducts: Int?,
    numberSelectedCategory: Int,
    modifier: Modifier,
    onFilterClicked: () -> Unit,
    onSortClicked: () -> Unit
) {
    Card(modifier = modifier
        .fillMaxWidth()
        .height(42.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.whisper)),
        elevation = CardDefaults.cardElevation(defaultElevation = 30.dp),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.whisper))
            ) {
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 16.dp), text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = FontFamily(Font(R.font.montserrat_semibold))
                                )
                            ) {
                                append(numberFilteredProducts?.toString() ?: "0000")
                                append(
                                    stringResource(id = R.string.product_items_counter)
                                )
                            }
                        }, style = MaterialTheme.typography.overline.copy(
                            color = colorResource(id = R.color.prussian_blue)
                        )
                    )
                    Spacer(modifier = Modifier.width(152.dp))
                    Icon(
                        painterResource(id = R.drawable.ic_sort),
                        contentDescription = null,
                        tint = colorResource(id = R.color.prussian_blue),
                        modifier = Modifier.clickable { onSortClicked() }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.sort),
                        style = MaterialTheme.typography.caption.copy(
                            fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                            color = colorResource(id = R.color.prussian_blue)
                        )
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        painterResource(id = R.drawable.ic_filter),
                        contentDescription = null,
                        tint = colorResource(id = R.color.prussian_blue),
                        modifier = Modifier.clickable { onFilterClicked() }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.filter),
                        style = MaterialTheme.typography.caption.copy(
                            fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                            color = colorResource(id = R.color.prussian_blue)
                        )
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .clip(RoundedCornerShape(7.dp))
                            .background(colorResource(id = R.color.prussian_blue))
                    ) {
                        Text(
                            text = numberSelectedCategory.toString() ?: "0",
                            modifier = Modifier
                                .align(Alignment.Center),
                            style = MaterialTheme.typography.overline.copy(
                                fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                                color = colorResource(id = R.color.white)
                            )
                        )

                    }
                }
            }
        })

}
