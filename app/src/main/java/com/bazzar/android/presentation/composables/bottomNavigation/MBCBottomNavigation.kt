package com.bazzar.android.presentation.composables.bottomNavigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bazzar.android.presentation.NavGraphs
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

    val screenWidth = LocalConfiguration.current.screenWidthDp

    val selectedDestination = remember { mutableStateOf(BottomNavItemDestination.Home.direction) }
    var selectedIndex by remember { mutableStateOf(0) }

    Box(
        modifier = modifier.background(Color.Transparent)
    ) {
        BottomNavigation(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter),
            backgroundColor = BazzarTheme.colors.white,
            contentColor = BazzarTheme.colors.bottomNavBarNonSelected,
            elevation = 4.dp
        ) {
            items.forEachIndexed { index, navItem ->
                val isSelected = navItem.direction == selectedDestination.value
                BottomNavigationItem(
                    modifier = Modifier.padding(bottom = BazzarTheme.spacing.m),
                    selected = isSelected,
                    onClick = {
                        selectedDestination.value = navItem.direction
                        selectedIndex = index
                        onTabSelected(navItem.tapIndex)
                        navController.onNavItemClick(navItem)
                    },

                    icon = {
                        if (isSelected.not()) {
                            Image(
                                modifier = Modifier.size(24.dp),
                                contentScale = ContentScale.FillBounds,
                                imageVector = ImageVector.vectorResource(id = navItem.vectorRes),
                                contentDescription = stringResource(id = navItem.title)
                            )
                        }
                    },
                    label = {
                        if (isSelected.not()) {
                            OverLine(
                                modifier = Modifier.wrapContentSize(),
                                text = stringResource(id = navItem.title).uppercase(),
                                color = BazzarTheme.colors.bottomNavBarSelected,
                                maxLines = 1,
                                style = BazzarTheme.typography.subtitle1SemiBold.copy(fontSize = 11.sp)
                            )
                        }
                    },
                    alwaysShowLabel = false,
                    selectedContentColor = BazzarTheme.colors.bottomNavBarSelected,
                    unselectedContentColor = BazzarTheme.colors.bottomNavBarNonSelected,
                )
            }
        }
        // Custom float icon when selected
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(
                    bottom = 36.dp,
                    start = ((selectedIndex * (screenWidth / items.size))).dp.plus(16.dp)
                )
                .width(52.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        color = BazzarTheme.colors.primaryButtonColor,
                        shape = RoundedCornerShape(26.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = BazzarTheme.colors.dodgerBlue,
                        shape = RoundedCornerShape(26.dp)
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    modifier = Modifier.size(24.dp),
                    contentScale = ContentScale.FillBounds,
                    imageVector = ImageVector.vectorResource(id = items[selectedIndex].vectorResSelected),
                    contentDescription = stringResource(id = items[selectedIndex].title)
                )
            }
            OverLine(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                text = stringResource(id = items[selectedIndex].title).uppercase(),
                color = BazzarTheme.colors.bottomNavBarSelected,
                maxLines = 1,
                style = BazzarTheme.typography.subtitle1SemiBold.copy(fontSize = 11.sp),
                textAlign = TextAlign.Center,
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
