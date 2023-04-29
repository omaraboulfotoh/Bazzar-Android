package com.bazzar.android.presentation.register.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.register.RegisterContract

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
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Text(
            text = stringResource(id = R.string.user_name),
            style = MaterialTheme.typography.subtitle2.copy(
                color = colorResource(id = R.color.prussian_blue),
                fontFamily = FontFamily(Font(R.font.montserrat_bold))
            ),
        )
        TextField(
            value = fullName,
            onValueChange = onNameChanged,
            modifier = Modifier
                .width(320.dp)
                .height(60.dp)
                .padding(top = 8.dp),
            placeholder = {
                Text(
                    text = stringResource(
                        id = R.string.type_name
                    ),
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
            value = email, onValueChange = onEmailChanged,
            modifier = Modifier
                .width(320.dp)
                .height(60.dp)
                .padding(top = 8.dp),
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
            value = phone, onValueChange = onPhoneChanged,
            modifier = Modifier
                .width(320.dp)
                .height(60.dp)
                .padding(top = 8.dp),
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
                Icon(
                    painter = painterResource(id = R.drawable.icon_ionic_logo_whatsapp),
                    contentDescription = null
                )
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

        // space to bottom
        Spacer(modifier = Modifier.weight(1f))


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