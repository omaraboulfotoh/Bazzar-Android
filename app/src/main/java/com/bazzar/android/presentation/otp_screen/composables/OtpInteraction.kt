package com.bazzar.android.presentation.otp_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.CustomButton

@Composable
fun OtpInteraction(modifier: Modifier) {
    Box(
        modifier = modifier
            .width(343.dp)
            .height(608.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color.White),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = stringResource(id = R.string.otp_enter_digits_guide),
            style = MaterialTheme.typography.subtitle2.copy(
                color = Color.Black, fontFamily = FontFamily(Font(R.font.montserrat_medium))
            ),
            modifier = Modifier
                .padding(top = 56.dp)
                .width(284.dp)
        )
        OtpTextField(
            modifier = Modifier
                .padding(top = 148.dp)
                .width(328.dp)
                .height(76.dp),
            otpCount = 4,
            onOtpTextChange = { _, _ ->
                {

                }
            }, otpText = ""
        )
        Text(
            modifier = Modifier
                .padding(top = 256.dp)
                .width(262.dp)
                .height(41.dp),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                ) {
                    append(stringResource(id = R.string.otp_send_again_guide))
                }
                withStyle(
                    style = SpanStyle(
                        fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                        fontSize = 14.sp,
                        color = colorResource(id = R.color.deep_sky_blue)
                    )
                ) {
                    append(stringResource(id = R.string.otp_send_again))
                }
            }
        )
        //Timer
        Text(
            text = "",
            style = MaterialTheme.typography.subtitle2.copy(
                color = colorResource(id = R.color.Gray59),
                fontFamily = FontFamily(Font(R.font.montserrat_regular))
            ),
            modifier = Modifier
                .padding(top = 370.dp)
                .width(40.dp)
        )
        CustomButton(
            text = stringResource(id = R.string.confirm),
            modifier = Modifier.padding(top = 472.dp)
        )
    }
}