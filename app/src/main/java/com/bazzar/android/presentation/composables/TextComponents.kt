package com.bazzar.android.presentation.composables

import android.os.Build
import android.text.Html
import android.widget.TextView
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.core.text.toSpannable
import com.bazzar.android.presentation.theme.BazzarTheme
import com.bazzar.android.presentation.theme.montserrat

@Composable
fun Title(
    text: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = BazzarTheme.colors.primaryText,
    isUpperCase: Boolean = true,
    style: TextStyle = BazzarTheme.typography.h7Bold,
) {
    Text(
        text = if (isUpperCase) text.uppercase() else text,
        modifier = modifier,
        maxLines = maxLines,
        textAlign = textAlign,
        color = color,
        style = style,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun SectionTitle(
    text: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
    color: Color = BazzarTheme.colors.black,
    style: TextStyle = BazzarTheme.typography.body2Bold,
) =
    Title(
        text,
        modifier,
        maxLines,
        textAlign,
        color,
        isUpperCase = false,
        style
    )

@Composable
fun MultipleStylesInText(
    text1: String, text2: String,
    modifier: Modifier = Modifier,
) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontFamily = montserrat,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            ) {
                append(text1)
            }

            withStyle(
                style = SpanStyle(
                    fontFamily = montserrat,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(text2)
            }
        }
    )
}

@Composable
fun DiscountStylesInText(
    text1: String, text2: String,
    modifier: Modifier = Modifier,
) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontFamily = montserrat,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(text1)
            }

            withStyle(
                style = SpanStyle(
                    fontFamily = montserrat,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Medium
                )
            ) {
                append(text2)
            }
        }
    )
}

@Composable
fun MessageBody(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = BazzarTheme.colors.secondaryText,
    style: TextStyle = BazzarTheme.typography.body2,
) {
    Text(
        text = text,
        modifier = modifier,
        maxLines = maxLines,
        textAlign = textAlign,
        color = color,
        style = style,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun Subtitle(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = BazzarTheme.colors.secondaryText,
    style: TextStyle = BazzarTheme.typography.subtitle1,
) {
    Text(
        text = text,
        modifier = modifier,
        maxLines = maxLines,
        textAlign = textAlign,
        color = color,
        style = style,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun MessageBodyBold(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = BazzarTheme.colors.primaryText,
    style: TextStyle = BazzarTheme.typography.body1Bold,
) = MessageBody(text, modifier, maxLines, textAlign, color, style)

@Composable
fun DescriptionBody(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = BazzarTheme.colors.primaryText,
    style: TextStyle = BazzarTheme.typography.body2,
) = MessageBody(text, modifier, maxLines, textAlign, color, style)

@Composable
fun DescriptionBody(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = BazzarTheme.colors.primaryText,
    style: TextStyle = BazzarTheme.typography.body2,
) = Text(
    text = text,
    modifier = modifier,
    maxLines = maxLines,
    textAlign = textAlign,
    color = color,
    style = style,
    overflow = TextOverflow.Ellipsis
)

@Composable
fun OverLine(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = BazzarTheme.colors.primaryText,
    style: TextStyle = BazzarTheme.typography.overline,
) = Text(
    text = text,
    modifier = modifier,
    maxLines = maxLines,
    textAlign = textAlign,
    color = color,
    style = style,
    overflow = TextOverflow.Ellipsis
)

@Composable
fun OverLineBold(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = BazzarTheme.colors.primaryText,
    style: TextStyle = BazzarTheme.typography.overlineBold,
) = OverLine(text, modifier, maxLines, textAlign, color, style)

@Composable
fun ClickableText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = BazzarTheme.colors.white,
    style: TextStyle = BazzarTheme.typography.h7Bold.copy(textDecoration = TextDecoration.Underline),
    textAlign: TextAlign = TextAlign.Start,
    isEnabled: Boolean = true,
) {
    Text(
        text = text,
        modifier = modifier.clickable(enabled = isEnabled, onClick = onClick),
        style = style,
        color = color,
        textAlign = textAlign,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun Header4Bold(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = BazzarTheme.colors.primaryText,
) =
    Text(
        text = text,
        modifier = modifier,
        maxLines = maxLines,
        textAlign = textAlign,
        color = color,
        style = BazzarTheme.typography.h4Bold,
        overflow = TextOverflow.Ellipsis
    )

@Composable
fun Caption(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = BazzarTheme.colors.black,
    isBold: Boolean = false,
) {
    Text(
        text = text,
        modifier = modifier,
        maxLines = maxLines,
        textAlign = textAlign,
        color = color,
        style = if (isBold) BazzarTheme.typography.captionSemiBold else BazzarTheme.typography.caption,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun ExpandingText(
    modifier: Modifier = Modifier,
    text: String,
    maxLinesSize: Int = 2,
    viewMoreActionText: String,
    viewLessActionText: String,
    color: Color = BazzarTheme.colors.secondaryText,
    actionColor: Color = BazzarTheme.colors.secondaryText,
) {

    val fullText =
        HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT)

    var isExpanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    var isClickable by remember { mutableStateOf(false) }
    var finalText by remember { mutableStateOf(fullText.toString()) }
    var dotText by remember { mutableStateOf("") }
    var actionText by remember { mutableStateOf("") }

    val textLayoutResult = textLayoutResultState.value
    LaunchedEffect(textLayoutResult) {
        if (textLayoutResult == null) return@LaunchedEffect

        when {
            isExpanded -> {
                finalText = "$fullText"
                actionText = viewLessActionText
                dotText = ""
            }

            !isExpanded && textLayoutResult.hasVisualOverflow -> {
                val lastCharIndex = textLayoutResult.getLineEnd(maxLinesSize - 1)
                val adjustedText = fullText
                    .substring(startIndex = 0, endIndex = lastCharIndex)
                    .dropLast(viewMoreActionText.length + TextComponentsConstants.DOTS_COUNTS)
                    .dropLastWhile { it == ' ' || it == '.' }

                finalText = "$adjustedText"
                actionText = viewMoreActionText
                dotText = "..."
                isClickable = true
            }
        }
    }

    MultipleStylesText(
        text1 = "$finalText$dotText ",
        text2 = actionText,
        style1 = BazzarTheme.typography.subtitleSpan.copy(color = color),
        style2 = BazzarTheme.typography.subtitleBoldSpan.copy(
            color = actionColor,
            textDecoration = TextDecoration.Underline
        ),
        maxLines = if (isExpanded) Int.MAX_VALUE else maxLinesSize,
        onTextLayout = { textLayoutResultState.value = it },
        modifier = modifier
            .clickable(enabled = isClickable) { isExpanded = !isExpanded }
            .animateContentSize(),
        textAlign = TextAlign.Start
    )
}

@Composable
fun HtmlText(
    html: String,
    color: Color = BazzarTheme.colors.primaryButtonColor,
    modifier: Modifier = Modifier
) {

    val fullText =
        HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)

    Caption(
        text = fullText.toString(),
        modifier = modifier,
        textAlign = TextAlign.Start,
        color = color
    )
}


object TextComponentsConstants {
    const val DOTS_COUNTS = 4
}

@Composable
fun MultipleStylesText(
    text1: String,
    text2: String,
    style1: SpanStyle,
    style2: SpanStyle,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Center,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = buildAnnotatedString {
            withStyle(style = style1) { append(text1) }
            withStyle(style = style2) { append(text2) }
        },
        textAlign = textAlign,
        maxLines = maxLines,
        onTextLayout = onTextLayout
    )
}


