package com.bazzar.android.presentation.search.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.SearchTextInput
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun SearchScreenHeader(
    searchTerm: String,
    onSearchTermChanged: (searchTerm: String) -> Unit,
    onSearchClicked: () -> Unit,
    onBackClicked: () -> Unit,
) {
    BazzarAppBar(
        actions = {
            SearchTextInput(
                modifier = Modifier
                    .padding(start = BazzarTheme.spacing.xxl)
                    .fillMaxWidth()
                    .background(BazzarTheme.colors.white)
                    .border(
                        width = 1.dp,
                        color = BazzarTheme.colors.stroke,
                        shape = RoundedCornerShape(24.dp)
                    ),
                hint = stringResource(id = R.string.product_search_title),
                value = searchTerm,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearchClicked() }),
                childRowModifier = Modifier.padding(vertical = BazzarTheme.spacing.xs),
                onValueChange = onSearchTermChanged,
                textStyle = BazzarTheme.typography.captionBold.copy(color = BazzarTheme.colors.primaryButtonColor),
                cursorColor = SolidColor(BazzarTheme.colors.primaryButtonColor),
                leadingIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = BazzarTheme.spacing.s)
                            .clickable { onSearchClicked() },
                        painter = painterResource(id = R.drawable.search_icon),
                        tint = BazzarTheme.colors.primaryButtonColor,
                        contentDescription = ""
                    )
                }
            )
        },
        onNavigationClick = onBackClicked
    )
}