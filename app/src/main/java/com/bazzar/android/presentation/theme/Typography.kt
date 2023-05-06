package com.bazzar.android.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.bazzar.android.R

internal val montserrat: FontFamily =
    FontFamily(
        Font(R.font.montserrat_regular),
        Font(R.font.montserrat_medium, FontWeight.Medium),
        Font(R.font.montserrat_light, FontWeight.Light),
        Font(R.font.montserrat_semibold, FontWeight.SemiBold),
        Font(R.font.montserrat_bold, FontWeight.Bold),
        Font(R.font.montserrat_extrabold, FontWeight.ExtraBold)
    )

val Typography = Typography(defaultFontFamily = montserrat)

/**
 * from my perspective TextStyles shouldn't have colors and should be abstract
 * colors should be applied later on for Text components
 * refer to @see [https://material.io/design/typography/the-type-system.html#type-scale]
 */
@Immutable
data class AbsTypography(
    val h1Bold: TextStyle =
        TextStyle(
            fontFamily = montserrat,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 42.sp
        ),
    val h2Bold: TextStyle =
        TextStyle(
            fontFamily = montserrat,
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 56.sp
        ),
    val h3Bold: TextStyle =
        TextStyle(
            fontFamily = montserrat,
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 48.sp
        ),
    val h4Bold: TextStyle =
        TextStyle(
            fontFamily = montserrat,
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 48.sp
        ),
    val h5: TextStyle =
        TextStyle(
            fontFamily = montserrat,
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal
        ),
    val h5Bold: TextStyle =
        h5.copy(fontWeight = FontWeight.Bold),
    val h5SemiBold: TextStyle =
        h5.copy(fontWeight = FontWeight.SemiBold),
    val h7Bold: TextStyle =
        h5Bold.copy(fontSize = 20.sp),
    val subtitle1: TextStyle =
        TextStyle(
            fontFamily = montserrat,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 26.sp
        ),
    val subtitle1Bold: TextStyle =
        subtitle1.copy(fontWeight = FontWeight.Bold),
    val subtitle1SemiBold: TextStyle =
        subtitle1.copy(fontWeight = FontWeight.SemiBold),
    val body1Medium: TextStyle =
        TextStyle(
            fontFamily = montserrat,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 24.sp
        ),
    val body1Bold: TextStyle = body1Medium.copy(fontWeight = FontWeight.Bold),
    val body1: TextStyle = body1Medium.copy(fontWeight = FontWeight.Normal),
    val body2: TextStyle =
        TextStyle(
            fontFamily = montserrat,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal
        ),
    val body2Bold: TextStyle =
        body2.copy(fontWeight = FontWeight.Bold),
    val body2Medium: TextStyle =
        body2.copy(fontWeight = FontWeight.Medium),

    val bottomSheetTextItemStyle: TextStyle = TextStyle(
        fontFamily = montserrat,
        fontSize = 23.sp,
        fontWeight = FontWeight.Normal
    ),
    val overline: TextStyle =
        TextStyle(
            fontFamily = montserrat,
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 24.sp
        ),
    val overlineBold: TextStyle =
        overline.copy(fontWeight = FontWeight.Bold),
    val caption: TextStyle =
        TextStyle(
            fontFamily = montserrat,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 18.sp
        ),
    val captionBold: TextStyle =
        caption.copy(fontWeight = FontWeight.Bold),
    val captionSemiBold: TextStyle =
        caption.copy(fontWeight = FontWeight.SemiBold),
    val captionMedium: TextStyle =
        caption.copy(fontWeight = FontWeight.Medium),
    val h7BoldSpan: SpanStyle = SpanStyle(
        fontFamily = montserrat,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        textDecoration = TextDecoration.Underline
    ),
    val subtitleBoldSpan: SpanStyle = SpanStyle(
        fontFamily = montserrat,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    ),
    val subtitleSpan: SpanStyle = SpanStyle(
        fontFamily = montserrat,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
)

val LocalAbsTypography = staticCompositionLocalOf { AbsTypography() }
