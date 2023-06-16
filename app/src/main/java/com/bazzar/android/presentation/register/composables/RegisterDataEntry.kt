package com.bazzar.android.presentation.register.composables

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun RegisterDataEntry(
    isTermsChecked: Boolean = false,
    fullName: String,
    phone: String,
    isArabic: Boolean,
    modifier: Modifier,
    onCreateAccount: () -> Unit,
    onPhoneChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
    onTermsAndConditionClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = BazzarTheme.spacing.m),
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m),
    ) {
        Column(modifier = Modifier.padding(bottom = BazzarTheme.spacing.m)) {
            Text(
                text = stringResource(id = R.string.user_name),
                style = MaterialTheme.typography.subtitle2.copy(
                    color = colorResource(id = R.color.prussian_blue),
                    fontFamily = FontFamily(Font(R.font.siwa_heavy))
                ),
            )
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
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions.Default,
                singleLine = true,
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(id = R.color.prussian_blue),
                    backgroundColor = BazzarTheme.colors.white,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                value = fullName,
                onValueChange = onNameChanged,
                shape = RoundedCornerShape(32.5.dp),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.type_name),
                        style = BazzarTheme.typography.caption,
                        color = BazzarTheme.colors.primaryButtonColor
                    )
                },
            )
            Text(
                text = stringResource(id = R.string.phone_number),
                style = MaterialTheme.typography.subtitle2.copy(
                    color = colorResource(id = R.color.prussian_blue),
                    fontFamily = FontFamily(Font(R.font.siwa_heavy))
                ),
                modifier = Modifier.padding(top = 24.dp)
            )

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
                    value = phone, onValueChange = onPhoneChanged,
                    shape = RoundedCornerShape(32.5.dp),
                    placeholder = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.type_number),
                            style = BazzarTheme.typography.caption,
                            color = BazzarTheme.colors.primaryButtonColor,
                            textAlign = if (isArabic) TextAlign.End else TextAlign.Start
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
            Text(
                modifier = Modifier.padding(top = 8.dp),
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
                        append(stringResource(id = R.string.otp_message))
                    }
                }, style = MaterialTheme.typography.overline.copy(
                    fontFamily = FontFamily(Font(R.font.siwa_heavy))
                )
            )
        }

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isTermsChecked,
                    onCheckedChange = { checked_ ->
                        onTermsAndConditionClicked()
                    },
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = stringResource(id = R.string.agree_terms_conditions),
                    style = MaterialTheme.typography.overline.copy(
                        color = colorResource(id = R.color.prussian_blue),
                        fontFamily = FontFamily(Font(R.font.siwa_heavy))
                    )
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp)
                        .clip(RoundedCornerShape(32.5.dp))
                        .background(colorResource(id = R.color.prussian_blue))
                        .align(Alignment.Center)
                        .clickable {
                            onCreateAccount()
                        }
                ) {
                    Text(
                        text = stringResource(id = R.string.create_account),
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = FontFamily(Font(R.font.siwa_heavy))
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}