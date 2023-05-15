package com.bazzar.android.presentation.changePassword.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun ChangePasswordDataEntry(
    modifier: Modifier = Modifier,
    currentPassword: String,
    newPassword: String,
    onCurrentPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onChangePasswordClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor)
            .padding(horizontal = BazzarTheme.spacing.m)
            .padding(bottom = BottomNavigationHeight),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.padding(top = BazzarTheme.spacing.m),
            text = stringResource(id = R.string.current_password),
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
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(vertical = 4.dp),
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(id = R.color.prussian_blue),
                backgroundColor = BazzarTheme.colors.white,
                focusedIndicatorColor = BazzarTheme.colors.primaryButtonColor,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            visualTransformation = PasswordVisualTransformation(),
            value = currentPassword,
            onValueChange = onCurrentPasswordChange,
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.enter_current_password),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                        color = colorResource(id = R.color.prussian_blue)
                    ),
                )
            },
        )
        Text(
            modifier = Modifier.padding(top = BazzarTheme.spacing.m),
            text = stringResource(id = R.string.new_password),
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
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(vertical = 4.dp),
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(id = R.color.prussian_blue),
                backgroundColor = BazzarTheme.colors.white,
                focusedIndicatorColor = BazzarTheme.colors.primaryButtonColor,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            visualTransformation = PasswordVisualTransformation(),
            value = newPassword,
            onValueChange = onNewPasswordChange,
            shape = RoundedCornerShape(16.dp),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.enter_new_password),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                        color = colorResource(id = R.color.prussian_blue)
                    ),
                )
            },
        )
        Box(Modifier.fillMaxWidth()) {
            Box(modifier = Modifier
                .padding(top = BazzarTheme.spacing.m)
                .fillMaxWidth()
                .height(65.dp)
                .clip(RoundedCornerShape(32.5.dp))
                .background(colorResource(id = R.color.prussian_blue))
                .align(Alignment.Center)
                .clickable {
                    onChangePasswordClick.invoke()
                }) {
                Text(
                    text = stringResource(id = R.string.change_password),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontFamily = FontFamily(Font(R.font.montserrat_bold))
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}