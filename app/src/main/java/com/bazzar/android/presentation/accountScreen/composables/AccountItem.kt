package com.bazzar.android.presentation.accountScreen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.model.home.UserData
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun AccountItem(
    modifier: Modifier = Modifier,
    userData: UserData?,
    onAccountItemClick: () -> Unit,
) {
    if (userData == null) return

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = BazzarTheme.spacing.m)
            .background(
                color = BazzarTheme.colors.primaryButtonColor,
                shape = RoundedCornerShape(BazzarTheme.spacing.xl)
            )
            .clickable { onAccountItemClick.invoke() }
            .padding(BazzarTheme.spacing.xl),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "",
            tint = BazzarTheme.colors.white,
        )
        Text(
            style = TextStyle(textAlign = TextAlign.Center),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = FontFamily(Font(R.font.siwa_regular)),
                        fontSize = 14.sp,
                        color = BazzarTheme.colors.white
                    )
                ) {
                    append(userData.name.orEmpty())
                }
                withStyle(
                    style = SpanStyle(
                        fontFamily = FontFamily(Font(R.font.siwa_heavy)),
                        fontSize = 14.sp,
                        color = BazzarTheme.colors.white
                    )
                ) {
                    append(if (userData.email.orEmpty().isNotEmpty()) "\n${userData.email}" else "")
                }
            }
        )
        Image(
            painter = painterResource(id = R.drawable.ic_bazzar_circular_icon),
            contentDescription = "",
        )
    }
}