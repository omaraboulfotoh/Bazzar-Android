package com.bazzar.android.presentation.product_detail_screen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.bazzar.android.R

@Composable
fun RatingRow(
    rating: Int,
    activeColor: Color = Color.Yellow,
    inactiveColor: Color = Color.Gray,
    updateRating:(Int)->Unit
) {
    Box(
        modifier = Modifier
            .width(375.dp)
            .height(47.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(id = R.string.give_rate),
                style = MaterialTheme.typography.overline.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_bold))
                )
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                for (index in 1..5) {
                    val icon = if (index <= rating) Icons.Filled.Star else Icons.Filled.Star
                    val color = if (index <= rating) activeColor else inactiveColor
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(24.dp).clickable {
                            updateRating(index)
                        }
                    )
                }
            }

            Text(
                text = stringResource(id = R.string.sold_by),
                style = MaterialTheme.typography.overline.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_bold))
                )
            )

        }
    }
}

@Composable
fun RatingStars() {
}