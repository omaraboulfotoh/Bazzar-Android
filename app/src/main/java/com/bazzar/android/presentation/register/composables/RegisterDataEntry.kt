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
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun RegisterDataEntry(
    isTermsChecked: Boolean = false,
    email: String,
    fullName: String,
    phone: String,
    modifier: Modifier,
    onCreateAccount: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onNameChanged: (String) -> Unit,
    onTermsAndConditionClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = BazzarTheme.spacing.spacerMini, vertical = BazzarTheme.spacing.l),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(modifier = Modifier.padding(bottom = 24.dp)) {
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
                shape = RoundedCornerShape(32.5.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(id = R.color.prussian_blue),
                    backgroundColor = BazzarTheme.colors.white,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                value = email, onValueChange = onEmailChanged,
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
                        modifier = Modifier.padding(horizontal = 8.dp),
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
                        fontFamily = FontFamily(Font(R.font.montserrat_bold))
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
                            fontFamily = FontFamily(Font(R.font.montserrat_bold))
                        ),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}