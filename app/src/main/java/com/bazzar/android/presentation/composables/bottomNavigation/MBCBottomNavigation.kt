package com.bazzar.android.presentation.composables.bottomNavigation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bazzar.android.R
import com.bazzar.android.presentation.NavGraphs
import com.bazzar.android.presentation.composables.OverLine
import com.bazzar.android.presentation.theme.BazzarTheme
import com.ramcosta.composedestinations.navigation.popUpTo
import com.ramcosta.composedestinations.spec.Direction

@Composable
fun MBCBottomNavigation(
    modifier: Modifier,
    navController: NavController,
    selectedIndex: Int = 0,
    numOfCartItems: Int = 0,
    selectedDestination: Direction = BottomNavItemDestination.Home.direction,
    onTabSelected: (Int) -> Unit,
) {
    val items = listOf(
        BottomNavItemDestination.Home,
        BottomNavItemDestination.Categories,
        BottomNavItemDestination.Bazzars,
        BottomNavItemDestination.Cart,
        BottomNavItemDestination.Profile
    )

    val screenWidth = LocalConfiguration.current.screenWidthDp


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
                val isSelected = index == selectedIndex
                BottomNavigationItem(
                    modifier = Modifier.padding(bottom = BazzarTheme.spacing.m),
                    selected = isSelected,
                    onClick = {
                        onTabSelected(navItem.tapIndex)
                        navController.onNavItemClick(navItem.direction)
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
                                maxLines = 2,
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

        var targetOffset by remember { mutableStateOf(0.dp) }
        val animatedOffset by animateDpAsState(
            targetValue = targetOffset,
            animationSpec = tween(durationMillis = 500)
        )

        LaunchedEffect(selectedIndex) {
            targetOffset =
                if (selectedIndex == 0) 16.dp
                else (selectedIndex * (screenWidth / items.size) + 16).dp
        }

        // Custom float icon when selected
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(bottom = 32.dp)
                .width(52.dp)
                .offset(x = animatedOffset)
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
                Box(contentAlignment = Alignment.Center) {
                    if (selectedIndex == 3 && numOfCartItems > 0) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "$numOfCartItems",
                            style = BazzarTheme.typography.captionMedium,
                            color = colorResource(id = R.color.deep_sky_blue)
                        )
                    }
                    Image(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(start = 2.dp)
                            .align(Alignment.BottomCenter),
                        contentScale = ContentScale.FillBounds,
                        imageVector = ImageVector.vectorResource(id = items[selectedIndex].vectorResSelected),
                        contentDescription = stringResource(id = items[selectedIndex].title)
                    )
                }
            }
            OverLine(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                text = stringResource(id = items[selectedIndex].title).uppercase(),
                color = colorResource(id = R.color.light_gray),
                maxLines = 1,
                style = BazzarTheme.typography.subtitle1SemiBold.copy(fontSize = 11.sp),
                textAlign = TextAlign.Center,
            )
        }
    }
}

fun NavController.onNavItemClick(navItem: Direction) {
    navigate(navItem.route) {
        popUpTo(NavGraphs.root) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

val BottomNavigationHeight = 80.dp
