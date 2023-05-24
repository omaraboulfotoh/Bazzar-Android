package com.bazzar.android.presentation.otpScreen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.CustomButton

@Composable
fun OtpInteraction(
    modifier: Modifier = Modifier,
    otp: String,
    onOtpTextChanged: (text: String, finalDigitEntered: Boolean) -> Unit,
    otpCount: Int = 4,
    onConfirmClicked: () -> Unit,
    onSendAgainClicked: () -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(top = 56.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.otp_enter_digits_guide),
                style = MaterialTheme.typography.subtitle2.copy(
                    color = Color.Black, fontFamily = FontFamily(Font(R.font.siwa_regular))
                ),
                textAlign = TextAlign.Center
            )
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                OtpTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 56.dp),
                    otpCount = otpCount,
                    onOtpTextChange = onOtpTextChanged,
                    otpText = otp
                )
            }
            ClickableText(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .fillMaxWidth(),
                style = TextStyle(textAlign = TextAlign.Center),
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily(Font(R.font.siwa_regular)),
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    ) {
                        append("${stringResource(id = R.string.otp_send_again_guide)}\n")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily(Font(R.font.siwa_heavy)),
                            fontSize = 14.sp,
                            color = colorResource(id = R.color.deep_sky_blue)
                        )
                    ) {
                        append(stringResource(id = R.string.otp_send_again))
                    }
                },
                onClick = { offset ->
                    if (offset in 39..48) {
                        onSendAgainClicked()
                    }
                }
            )
            //Timer
            CountdownTimer(60)
        }

        CustomButton(
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable { onConfirmClicked() },
            text = stringResource(id = R.string.confirm),
            isDisable = otp.length < otpCount,
            onClick = onConfirmClicked,
        )
    }
}