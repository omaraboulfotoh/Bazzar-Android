package com.bazzar.android.presentation.productsList.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.model.home.SortItem
import com.bazzar.android.R
import com.bazzar.android.common.advancedShadow
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun SortDialog(
    modifier: Modifier = Modifier,
    show: Boolean = false,
    sortingList: List<SortItem>,
    selectedSortItem: SortItem?,
    onDismiss: () -> Unit,
    onSelectSortItem: (sortItem: SortItem) -> Unit,
    onApply: () -> Unit,
) {

    if (show.not() || sortingList.isEmpty()) return

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        elevation = CardDefaults.cardElevation(defaultElevation = 32.dp),
        shape = RoundedCornerShape(24.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = BazzarTheme.spacing.m)
                .padding(bottom = BazzarTheme.spacing.m),
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.End),
                onClick = onDismiss
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close_circular),
                    modifier = Modifier.size(18.dp),
                    tint = colorResource(id = R.color.prussian_blue),
                    contentDescription = ""
                )
            }

            Text(
                text = stringResource(id = R.string.sorting),
                style = BazzarTheme.typography.subtitle1Bold,
                color = colorResource(id = R.color.prussian_blue)
            )

            Divider(
                modifier = Modifier
                    .padding(top = 14.dp, bottom = 16.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = colorResource(id = R.color.Gray59))
            )

            sortingList.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = BazzarTheme.spacing.xs),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(
                        modifier = Modifier.size(18.dp),
                        selected = selectedSortItem?.sortKey == item.sortKey,
                        onClick = { onSelectSortItem(item) }
                    )
                    Text(
                        modifier = Modifier.padding(start = BazzarTheme.spacing.xs),
                        text = item.title ?: "",
                        style = BazzarTheme.typography.subtitle3SemiBold,
                        color = colorResource(id = R.color.black)
                    )
                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .padding(top = BazzarTheme.spacing.xs)
                    .advancedShadow(
                        cornersRadius = 33.dp,
                        shadowBlurRadius = 15.dp,
                        alpha = 0.5f,
                        offsetX = 5.dp,
                        offsetY = 5.dp,
                    )
                    .clip(RoundedCornerShape(33.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BazzarTheme.colors.primaryButtonColor
                ),
                onClick = onApply
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = stringResource(id = R.string.apply),
                    style = BazzarTheme.typography.body2Bold,
                    color = BazzarTheme.colors.white,
                    fontSize = 14.sp,
                )
            }

        }
    }
}