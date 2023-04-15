package com.bazzar.android.presentation.composables.bottomNavigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.bazzar.android.R
import com.bazzar.android.presentation.category_screen.CategoryScreen
import com.bazzar.android.presentation.destinations.*
import com.bazzar.android.presentation.main.MainContract

sealed class BottomNavItemDestination(
    @StringRes val tapIndex: Int,
    @StringRes val title: Int,
    @DrawableRes val vectorRes: Int,
    @DrawableRes val vectorResSelected: Int,
    val direction: DirectionDestination,
) {
    object Home :
        BottomNavItemDestination(
            MainContract.HOME_TAB,
            R.string.home,
            R.drawable.ic_home_menu,
            R.drawable.ic_home,
            HomeScreenDestination
        )

    object Categories :
        BottomNavItemDestination(
            MainContract.CATEGORIES_TAB,
            R.string.categories,
            R.drawable.ic_category,
            R.drawable.ic_category,
            CategoryScreenDestination
        )

    object Cart :
        BottomNavItemDestination(
            MainContract.CART_TAB,
            R.string.cart,
            R.drawable.ic_cart,
            R.drawable.ic_cart_icon,
            SplashScreenDestination
        )

    object Profile :
        BottomNavItemDestination(
            MainContract.PROFILE_TAB,
            R.string.profile,
            R.drawable.ic_profile,
            R.drawable.ic_profile,
            SplashScreenDestination
        )
}
