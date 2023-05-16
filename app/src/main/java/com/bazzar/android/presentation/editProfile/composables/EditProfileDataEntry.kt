package com.bazzar.android.presentation.editProfile.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun EditProfileDataEntry(
    modifier: Modifier = Modifier,
    email: String,
    fullName: String,
    phone: String,
    onEmailChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
    onEditProfileClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Button(
            shape = RoundedCornerShape(28.dp),
            onClick = onChangePasswordClick
        ) {
            Text(
                modifier = Modifier.padding(BazzarTheme.spacing.m),
                text = stringResource(id = R.string.change_password),
                style = BazzarTheme.typography.captionBold,
                color = BazzarTheme.colors.white,
            )
        }
        Column(modifier = Modifier.padding(top = 16.dp, bottom = 88.dp)) {
            Text(
                text = stringResource(id = R.string.user_name),
                style = MaterialTheme.typography.subtitle2.copy(
                    color = colorResource(id = R.color.prussian_blue),
                    fontFamily = FontFamily(Font(R.font.montserrat_bold))
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
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                            color = colorResource(id = R.color.prussian_blue)
                        ),
                    )
                },
            )
            Text(
                text = stringResource(id = R.string.email),
                style = MaterialTheme.typography.subtitle2.copy(
                    color = colorResource(id = R.color.prussian_blue),
                    fontFamily = FontFamily(Font(R.font.montserrat_bold))
                ),
                modifier = Modifier.padding(top = 24.dp)
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
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                maxLines = 1,
                shape = RoundedCornerShape(32.5.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(id = R.color.prussian_blue),
                    backgroundColor = BazzarTheme.colors.white,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                value = email,
                onValueChange = onEmailChanged,
                placeholder = {
                    Text(
                        text = stringResource(
                            id = R.string.type_email
                        ),
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                            color = colorResource(id = R.color.prussian_blue)
                        ),
                    )
                },
            )
            Text(
                text = stringResource(id = R.string.phone_number),
                style = MaterialTheme.typography.subtitle2.copy(
                    color = colorResource(id = R.color.prussian_blue),
                    fontFamily = FontFamily(Font(R.font.montserrat_bold))
                ),
                modifier = Modifier.padding(top = 24.dp)
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
                singleLine = true,
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(id = R.color.prussian_blue),
                    backgroundColor = BazzarTheme.colors.white,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                value = phone,
                enabled = false,
                onValueChange = { },
                shape = RoundedCornerShape(32.5.dp),
                placeholder = {
                    Text(
                        text = stringResource(
                            id = R.string.type_number
                        ),
                        style = MaterialTheme.typography.subtitle1.copy(
                            fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                            color = colorResource(id = R.color.prussian_blue)
                        )
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
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = colorResource(id = R.color.prussian_blue)
                        )
                    ) {
                        append(stringResource(id = R.string.well_will_send))
                    }
                    withStyle(
                        style = SpanStyle(
                            color = colorResource(id = R.color.Malachite)
                        )
                    ) {
                        append(stringResource(id = R.string.whatsApp))
                    }
                    withStyle(
                        style = SpanStyle(
                            color = colorResource(id = R.color.prussian_blue)
                        )
                    ) {
                        append(stringResource(id = R.string.otp_message))
                    }
                }, style = MaterialTheme.typography.overline.copy(
                    fontFamily = FontFamily(Font(R.font.montserrat_bold))
                )
            )
        }
        Box(Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
                    .clip(RoundedCornerShape(32.5.dp))
                    .background(colorResource(id = R.color.prussian_blue))
                    .align(Alignment.Center)
                    .clickable {
                        onEditProfileClick()
                    }
            ) {
                Text(
                    text = stringResource(id = R.string.edit_account),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_bold))
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}