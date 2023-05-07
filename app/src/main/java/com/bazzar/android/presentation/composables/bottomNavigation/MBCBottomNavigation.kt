package com.bazzar.android.presentation.composables.bottomNavigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bazzar.android.presentation.NavGraphs
import com.bazzar.android.presentation.appCurrentDestinationAsState
import com.bazzar.android.presentation.composables.OverLine
import com.bazzar.android.presentation.theme.BazzarTheme
import com.ramcosta.composedestinations.navigation.popUpTo

@Composable
fun MBCBottomNavigation(
    navController: NavController,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier,
) {
    val items = listOf(
        BottomNavItemDestination.Home,
        BottomNavItemDestination.Categories,
        BottomNavItemDestination.Bazzars,
        BottomNavItemDestination.Cart,
        BottomNavItemDestination.Profile
    )

    val currentDestination = navController.appCurrentDestinationAsState().value
    BottomNavigation(
        modifier = modifier,
        backgroundColor = BazzarTheme.colors.white,
        contentColor = BazzarTheme.colors.bottomNavBarNonSelected,
        elevation = 4.dp
    ) {
        Column(modifier = Modifier, verticalArrangement = Arrangement.Top) {}
        items.forEach { navItem ->
            val isSelected = navItem.direction == currentDestination

            BottomNavigationItem(
                modifier = Modifier.padding(bottom = BazzarTheme.spacing.m),
                selected = isSelected,
                onClick = {
                    onTabSelected(navItem.tapIndex)
                    navController.onNavItemClick(navItem)
                },

                icon = {
                    Image(
                        modifier = Modifier
                            .size(24.dp),
                        contentScale = ContentScale.FillBounds,
                        imageVector = ImageVector.vectorResource(
                            id =
                            if (isSelected) navItem.vectorResSelected else navItem.vectorRes
                        ),
                        contentDescription = stringResource(id = navItem.title)
                    )
                },
                label = {
                    OverLine(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(top = BazzarTheme.spacing.xs),
                        text = stringResource(id = navItem.title).uppercase(),
                        color = BazzarTheme.colors.bottomNavBarSelected,
                        maxLines = 1,
                        style = BazzarTheme.typography.subtitle1SemiBold.copy(fontSize = 11.sp)
                    )
                },
                alwaysShowLabel = false,
                selectedContentColor = BazzarTheme.colors.bottomNavBarSelected,
                unselectedContentColor = BazzarTheme.colors.bottomNavBarNonSelected,
            )
        }
    }
}

private fun NavController.onNavItemClick(navItem: BottomNavItemDestination) {
    navigate(navItem.direction.route) {
        popUpTo(NavGraphs.root) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

val BottomNavigationHeight = 80.dp
