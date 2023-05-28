package com.bazzar.android.presentation.forgetPassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.R
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.theme.BazzarTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Composable
@Destination
fun ForgetPasswordScreen(
    viewModel: ForgetPasswordViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    phoneNumber: String
) {
    // get state
    val state = viewModel.viewState()
    viewModel.sideEffect { effect ->
        when (effect) {
            ForgetPasswordContract.Effect.Navigation.GoBack ->
                navigator.navigateUp()
        }
    }
    // init logic
    viewModel.init(phoneNumber)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = BottomNavigationHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
    ) {
        BazzarAppBar(
            title = stringResource(id = R.string.forget_password_text),
            onNavigationClick = {
                viewModel.setEvent(ForgetPasswordContract.Event.OnBackClicked)
            })
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(BazzarTheme.spacing.m),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs)
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = BazzarTheme.spacing.xs)
                        .border(
                            width = 1.dp,
                            color = BazzarTheme.colors.primaryButtonTextColorDisabled,
                            shape = RoundedCornerShape(32.5.dp),
                        )
                        .padding(vertical = 4.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    maxLines = 1,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = colorResource(id = R.color.prussian_blue),
                        backgroundColor = BazzarTheme.colors.white,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    value = state.phoneNumber.orEmpty(), onValueChange = {
                        viewModel.setEvent(ForgetPasswordContract.Event.OnPhoneChanged(it))
                    },
                    shape = RoundedCornerShape(32.5.dp),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.type_number),
                            style = BazzarTheme.typography.caption,
                            color = BazzarTheme.colors.primaryButtonColor
                        )
                    },
                    leadingIcon = {
                        Row(
                            modifier = Modifier.padding(
                                start = BazzarTheme.spacing.m,
                                end = BazzarTheme.spacing.xs
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.icon_ionic_logo_whatsapp),
                                contentDescription = null
                            )
                            Text(text = "+965", color = BazzarTheme.colors.black)
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier,
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = colorResource(id = R.color.prussian_blue)
                        )
                    ) {
                        append(stringResource(id = R.string.well_will_send))
                        append(" ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = colorResource(id = R.color.Malachite)
                        )
                    ) {
                        append(stringResource(id = R.string.whatsApp))
                        append(" ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = colorResource(id = R.color.prussian_blue)
                        )
                    ) {
                        append(stringResource(id = R.string.otp_message_password))
                    }
                }, style = MaterialTheme.typography.overline.copy(
                    fontFamily = FontFamily(Font(R.font.siwa_heavy))
                )
            )

            Box(Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp)
                        .clip(RoundedCornerShape(32.5.dp))
                        .background(colorResource(id = R.color.prussian_blue))
                        .align(Alignment.Center)
                        .clickable {
                            viewModel.setEvent(ForgetPasswordContract.Event.OnSendClicked)
                        }
                ) {
                    Text(
                        text = stringResource(id = R.string.send),
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = FontFamily(Font(R.font.siwa_heavy))
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            Spacer(modifier = Modifier.height(BazzarTheme.spacing.m))
        }
    }
}