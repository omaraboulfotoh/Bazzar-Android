package com.bazzar.android.presentation.theme

import androidx.compose.material.lightColors
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

val COLOR_NAVY: Color = Color(0xff000760)
val COLOR_BLUE: Color = Color(0Xff00B7FF)
val COLOR_LIGHT_BLUE = Color(0xffbff2ff)
val COLOR_RED: Color = Color(0XffE82357)
val COLOR_BLACK: Color = Color(0Xff0F1015)
val COLOR_WHITE: Color = Color(0Xffffffff)
val COLOR_TRANSPARENT: Color = Color(0X00ffffff)
val COLOR_LIGHT_SILVER: Color = Color(0XffF8F8F8)
val COLOR_LIGHT_GREY: Color = Color(0XffD1D1D1)
val COLOR_DARK_SILVER: Color = Color(0XffEAEAEA)
val COLOR_DARK_GREY: Color = Color(0Xff8C8C8C)
val PrimaryGradient =
    Brush.verticalGradient(listOf(COLOR_BLACK, COLOR_DARK_GREY), tileMode = TileMode.Decal)


val ColorPalette =
    lightColors(
        primary = COLOR_NAVY,
        primaryVariant = COLOR_BLUE,
        secondary = COLOR_RED,
        secondaryVariant = COLOR_BLACK,
        background = COLOR_LIGHT_SILVER,
        surface = COLOR_WHITE,
        onPrimary = COLOR_WHITE,
        onSecondary = COLOR_BLACK,
        onBackground = COLOR_WHITE,
        onSurface = COLOR_BLACK
    )

@Immutable
data class AbsColors(
    val bottomNavBarBackground: Color = COLOR_WHITE,
    val bottomNavBarSelected: Color = COLOR_BLUE,
    val bottomNavBarNonSelected: Color = Color(0X00dbdbdb),
    val backgroundColor: Color = COLOR_LIGHT_SILVER,
    val transparentColor: Color = COLOR_TRANSPARENT,
    val primaryButtonColor: Color = COLOR_NAVY,
    val primaryButtonDisableColor: Color = COLOR_DARK_GREY,
    val primaryButtonTextColor: Color = COLOR_WHITE,
    val primaryButtonTextColorDisabled: Color = COLOR_DARK_SILVER,
    val primaryText: Color = COLOR_WHITE,
    val secondaryText: Color = COLOR_BLACK,
    val discountText: Color = COLOR_RED,
    val stroke: Color = COLOR_LIGHT_GREY,
    val black: Color = COLOR_BLACK,
    val white: Color = COLOR_WHITE,
    val indicatorGrey: Color = COLOR_DARK_GREY,
    val placeholder: Color = COLOR_DARK_GREY,
    val borderColor: Color = COLOR_DARK_GREY,
    val pickerHeader: Color = COLOR_BLACK,
    val pickerContent: Color = COLOR_BLACK,
    val pickerOption: Color = COLOR_WHITE,
    val primaryGradient: Brush = PrimaryGradient,
    val textGray: Color = COLOR_DARK_GREY,
    val textHint: Color = COLOR_DARK_GREY,
    val indicatorActiveColor: Color = COLOR_NAVY,
    val indicatorInActiveColor: Color = COLOR_WHITE,
    val spacer: Color = COLOR_LIGHT_SILVER,
    val progressBg: Color = COLOR_NAVY,
    val dividerColor: Color = COLOR_DARK_GREY,
    val dodgerBlue: Color = Color(0xff00B7FF)
)

val LocalAbsColor = staticCompositionLocalOf { AbsColors() }
