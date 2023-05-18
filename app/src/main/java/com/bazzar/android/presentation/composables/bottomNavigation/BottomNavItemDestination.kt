package com.bazzar.android.presentation.composables.bottomNavigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.bazzar.android.R
import com.bazzar.android.presentation.destinations.*
import com.bazzar.android.presentation.main.MainContract
import com.ramcosta.composedestinations.spec.Direction

sealed class BottomNavItemDestination(
    @StringRes val tapIndex: Int,
    @StringRes val title: Int,
    @DrawableRes val vectorRes: Int,
    @DrawableRes val vectorResSelected: Int,
    val direction: Direction,
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
            R.drawable.ic_category_selected,
            CategoryScreenDestination()
        )

    object Bazzars :
        BottomNavItemDestination(
            MainContract.CART_TAB,
            R.string.bazzar,
            R.drawable.ic_bazzar_circular,
            R.drawable.ic_bazzar_circular_icon,
            BazarListScreenDestination
        )

    object Cart :
        BottomNavItemDestination(
            MainContract.CART_TAB,
            R.string.cart,
            R.drawable.ic_cart,
            R.drawable.ic_cart_icon,
            CartScreenDestination
        )

    object Profile :
        BottomNavItemDestination(
            MainContract.PROFILE_TAB,
            R.string.profile,
            R.drawable.ic_profile,
            R.drawable.ic_profile_selected,
            AccountScreenDestination
        )
}
