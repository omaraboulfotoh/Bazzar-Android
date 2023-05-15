package com.bazzar.android.presentation.search.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun RecentSearchList(
    recentSearchList: List<String>,
    onRemoveClick: (index: Int, searchTerm: String) -> Unit,
    onClick: (searchTerm: String) -> Unit,
) {
    if (recentSearchList.isEmpty()) return

    Column(
        modifier = Modifier
            .padding(top = BazzarTheme.spacing.l)
            .padding(horizontal = BazzarTheme.spacing.m),
    ) {
        Text(
            text = stringResource(id = R.string.recent_search),
            style = BazzarTheme.typography.body2Bold,
            color = BazzarTheme.colors.black,
        )
        Row(
            modifier = Modifier
                .padding(top = BazzarTheme.spacing.m)
                .fillMaxSize()
        ) {
            recentSearchList.forEachIndexed { index, s ->
                RecentSearchItem(
                    term = s,
                    onClick = { onClick(s) },
                    onRemoveClick = { onRemoveClick(index, s) }
                )
            }
        }
    }
}

@Composable
private fun RecentSearchItem(
    term: String,
    onRemoveClick: () -> Unit,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(end = BazzarTheme.spacing.xs)
            .border(
                width = 1.dp,
                BazzarTheme.colors.stroke,
                shape = RoundedCornerShape(18.dp)
            )
            .background(
                color = BazzarTheme.colors.white,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(
                horizontal = BazzarTheme.spacing.m,
                vertical = BazzarTheme.spacing.xs
            ).clickable { onClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(end = BazzarTheme.spacing.m),
            text = term,
            style = BazzarTheme.typography.overlineBold,
            color = BazzarTheme.colors.borderColor,
        )
        Icon(
            modifier = Modifier
                .size(18.dp)
                .clickable { onRemoveClick() },
            painter = painterResource(id = R.drawable.ic_close_circular),
            tint = BazzarTheme.colors.stroke,
            contentDescription = ""
        )
    }
}