package com.bazzar.android.presentation.productsList.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
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
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun SortFilterBar(
    modifier: Modifier = Modifier,
    numOfProducts: Int?,
    numOfSelectedFilters: Int,
    onFilterClicked: () -> Unit,
    onSortClicked: () -> Unit
) {
    Card(modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.whisper)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${numOfProducts ?: "0000"} ${stringResource(id = R.string.product_items_counter)}",
                    style = BazzarTheme.typography.subtitle3SemiBold,
                    color = colorResource(id = R.color.prussian_blue)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        modifier = Modifier.clickable { onSortClicked() },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_sort),
                            contentDescription = null,
                            tint = colorResource(id = R.color.prussian_blue),
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp, end = 16.dp),
                            text = stringResource(id = R.string.sort),
                            style = BazzarTheme.typography.body2Medium,
                            color = colorResource(id = R.color.prussian_blue),
                        )
                    }

                    Row(
                        modifier = Modifier.clickable { onFilterClicked() },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_filter),
                            contentDescription = null,
                            tint = colorResource(id = R.color.prussian_blue),

                            )
                        Text(
                            modifier = Modifier.padding(start = 8.dp, end = 4.dp),
                            text = stringResource(id = R.string.filter),
                            style = BazzarTheme.typography.body2Medium,
                            color = colorResource(id = R.color.prussian_blue),
                        )
                    }

                    if (numOfSelectedFilters != 0) {
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(RoundedCornerShape(7.dp))
                                .background(colorResource(id = R.color.prussian_blue))
                        ) {
                            Text(
                                text = numOfSelectedFilters.toString(),
                                modifier = Modifier.align(Alignment.Center),
                                style = BazzarTheme.typography.subtitle3,
                                color = colorResource(id = R.color.white),
                            )
                        }
                    }

                }
            }
        })

}
