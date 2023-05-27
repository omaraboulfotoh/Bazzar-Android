package com.bazzar.android.presentation.contactUs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bazzar.android.R
import com.bazzar.android.common.sideEffect
import com.bazzar.android.common.viewState
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.login.composables.InputMobileNumber
import com.bazzar.android.presentation.theme.BazzarTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ContactUsScreen(
    viewModel: ContactUsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {


    val state = viewModel.viewState()
    val scrollState = rememberScrollState()

    viewModel.sideEffect { effect ->
        when (effect) {
            ContactUsContract.Effect.Navigation.GoBack -> navigator.navigateUp()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = BottomNavigationHeight),
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs)
    ) {
        BazzarAppBar(title = stringResource(id = R.string.contact_us), onNavigationClick = {
            viewModel.setEvent(ContactUsContract.Event.OnBackClicked)
        })
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(BazzarTheme.spacing.m)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
        ) {

            Text(
                text = stringResource(id = R.string.full_name),
                style = MaterialTheme.typography.subtitle2.copy(
                    color = colorResource(id = R.color.prussian_blue),
                    fontFamily = FontFamily(Font(R.font.siwa_heavy))
                ),
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
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
                value = state.fullName.orEmpty(),
                onValueChange = {
                    viewModel.setEvent(ContactUsContract.Event.OnNameChanged(it))
                },
                shape = RoundedCornerShape(32.5.dp),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.type_name),
                        style = BazzarTheme.typography.caption,
                        color = BazzarTheme.colors.primaryButtonColor
                    )
                },
            )

            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = stringResource(id = R.string.email),
                style = MaterialTheme.typography.subtitle2.copy(
                    color = colorResource(id = R.color.prussian_blue),
                    fontFamily = FontFamily(Font(R.font.siwa_heavy))
                ),
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
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
                value = state.email.orEmpty(),
                onValueChange = {
                    viewModel.setEvent(ContactUsContract.Event.OnEmailChanged(it))
                },
                shape = RoundedCornerShape(32.5.dp),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.type_email),
                        style = BazzarTheme.typography.caption,
                        color = BazzarTheme.colors.primaryButtonColor
                    )
                },
            )

            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = stringResource(id = R.string.phone_number),
                style = MaterialTheme.typography.subtitle2.copy(
                    color = colorResource(id = R.color.prussian_blue),
                    fontFamily = FontFamily(Font(R.font.siwa_heavy))
                ),
            )
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                InputMobileNumber(
                    phone = state.phoneNumber.orEmpty(),
                    onPhoneChanged = {
                        viewModel.setEvent(ContactUsContract.Event.OnPhoneChanged(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = BazzarTheme.colors.primaryButtonTextColorDisabled,
                            shape = RoundedCornerShape(32.5.dp),
                        )
                        .padding(vertical = 4.dp),
                )
            }
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = stringResource(id = R.string.subject),
                style = MaterialTheme.typography.subtitle2.copy(
                    color = colorResource(id = R.color.prussian_blue),
                    fontFamily = FontFamily(Font(R.font.siwa_heavy))
                ),
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
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
                value = state.subject.orEmpty(),
                onValueChange = {
                    viewModel.setEvent(ContactUsContract.Event.OnSubjectChanged(it))
                },
                shape = RoundedCornerShape(32.5.dp),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.subject),
                        style = BazzarTheme.typography.caption,
                        color = BazzarTheme.colors.primaryButtonColor
                    )
                },
            )

            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = stringResource(id = R.string.message),
                style = MaterialTheme.typography.subtitle2.copy(
                    color = colorResource(id = R.color.prussian_blue),
                    fontFamily = FontFamily(Font(R.font.siwa_heavy))
                ),
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = BazzarTheme.colors.primaryButtonTextColorDisabled,
                        shape = RoundedCornerShape(32.5.dp),
                    )
                    .padding(vertical = 4.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
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
                value = state.message.orEmpty(),
                onValueChange = {
                    viewModel.setEvent(ContactUsContract.Event.OnMessageChanged(it))
                },
                shape = RoundedCornerShape(32.5.dp),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.message),
                        style = BazzarTheme.typography.caption,
                        color = BazzarTheme.colors.primaryButtonColor
                    )
                },
            )
            Spacer(modifier = Modifier.height(1.dp))

            Box(Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp)
                        .clip(RoundedCornerShape(32.5.dp))
                        .background(colorResource(id = R.color.prussian_blue))
                        .align(Alignment.Center)
                        .clickable {
                            viewModel.setEvent(ContactUsContract.Event.OnSubmitClicked)
                        }
                ) {
                    Text(
                        text = stringResource(id = R.string.submit),
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