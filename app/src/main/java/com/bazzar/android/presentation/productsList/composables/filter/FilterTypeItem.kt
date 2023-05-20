package com.bazzar.android.presentation.productsList.composables.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun FilterTypeItem(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean = false,
    numOfSelectedFilters: Int = 0,
    onFilterTypeClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(if (isSelected) BazzarTheme.colors.backgroundColor else Color.Transparent)
            .clickable { onFilterTypeClick.invoke() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = BazzarTheme.spacing.m),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = BazzarTheme.typography.subtitle1Medium,
                color = colorResource(id = R.color.black)
            )
            if (numOfSelectedFilters != 0) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .background(color = Color.Transparent, shape = RoundedCornerShape(7.dp))
                        .border(width = 1.dp, color = colorResource(id = R.color.deep_sky_blue), shape = RoundedCornerShape(7.dp))
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = numOfSelectedFilters.toString(),
                        style = BazzarTheme.typography.subtitle3,
                        color = colorResource(id = R.color.black),
                    )
                }
            }
        }

        Divider(
            modifier = Modifier
                .padding(top = BazzarTheme.spacing.xs)
                .height(1.dp)
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.gallery))
        )
    }
}