package com.bazzar.android.presentation.bazarListScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
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
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = BottomNavigationHeight),
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BazzarAppBar(
            title = stringResource(id = R.string.app_name),
            navigationIcon = null
        )
        SearchTextInput(
            modifier = Modifier
                .defaultMinSize(minHeight = 36.dp)
                .padding(vertical = BazzarTheme.spacing.xs)
                .padding(horizontal = BazzarTheme.spacing.m)
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(BazzarTheme.colors.white)
                .border(
                    width = 0.5.dp,
                    color = BazzarTheme.colors.stroke,
                    shape = RoundedCornerShape(24.dp)
                ),
            hint = stringResource(id = R.string.search_by_bzar_name),
            value = state.searchTerm ?: "",
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            childRowModifier = Modifier.padding(vertical = BazzarTheme.spacing.xs),
            onValueChange = { onSendEvent(BazarListContract.Event.OnSearchTermChanged(it)) },
            textStyle = BazzarTheme.typography.captionBold.copy(color = BazzarTheme.colors.primaryButtonColor),
            cursorColor = SolidColor(BazzarTheme.colors.primaryButtonColor),
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .padding(
                            start = BazzarTheme.spacing.briefingIconsDimen,
                            end = BazzarTheme.spacing.s
                        ),
                    painter = painterResource(id = R.drawable.search_icon),
                    tint = BazzarTheme.colors.primaryButtonColor,
                    contentDescription = ""
                )
            }
        )
        BazarList(
            modifier = Modifier
                .weight(1f)
                .background(color = BazzarTheme.colors.backgroundColor),
            bazarList = state.availableBazars.orEmpty(),
            onBazarItemFavClick = {
                onSendEvent(BazarListContract.Event.OnBazarItemFavClicked(it))
            }, onBazarItemClick = {
                onSendEvent(BazarListContract.Event.OnBazarItemClicked(it))
            }
        )
    }
}