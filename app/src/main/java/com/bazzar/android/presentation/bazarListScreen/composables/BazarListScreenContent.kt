package com.bazzar.android.presentation.bazarListScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.bazarListScreen.BazarListContract
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.SearchTextInput
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun BazarListScreenContent(
    state: BazarListContract.State,
    onSendEvent: (BazarListContract.Event) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BazzarAppBar(
            title = stringResource(id = R.string.bazzar),
            navigationIcon = null
        )
        SearchTextInput(
            modifier = Modifier
                .padding(top = BazzarTheme.spacing.l)
                .padding(horizontal = BazzarTheme.spacing.m)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = BazzarTheme.colors.stroke,
                    shape = RoundedCornerShape(24.dp)
                ),
            childRowModifier = Modifier.padding(vertical = BazzarTheme.spacing.m),
            hint = stringResource(id = R.string.search_by_bzar_name),
            value = state.searchTerm ?: "",
            onValueChange = { onSendEvent(BazarListContract.Event.OnSearchTermChanged(it)) },
            textStyle = BazzarTheme.typography.body2.copy(color = BazzarTheme.colors.primaryButtonColor),
            cursorColor = SolidColor(BazzarTheme.colors.primaryButtonColor),
            leadingIcon = {
                Icon(
                    modifier = Modifier.padding(horizontal = BazzarTheme.spacing.s),
                    painter = painterResource(id = R.drawable.search_icon),
                    tint = BazzarTheme.colors.primaryButtonColor,
                    contentDescription = ""
                )
            }
        )
        BazarList(
            modifier = Modifier
                .weight(1f)
                .background(color = BazzarTheme.colors.backgroundColor)
                .padding(bottom = BottomNavigationHeight),
            bazarList = state.availableBazars.orEmpty(),
            onBazarItemFavClick = {
                onSendEvent(BazarListContract.Event.OnBazarItemFavClicked(it))
            }, onBazarItemClick = {
                onSendEvent(BazarListContract.Event.OnBazarItemClicked(it))
            }
        )
    }
}