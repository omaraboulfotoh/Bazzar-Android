package com.bazzar.android.presentation.editProfile.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.editProfile.EditProfileContract
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun EditProfileScreenContent(
    state: EditProfileContract.State, onSendEvent: (EditProfileContract.Event) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = BottomNavigationHeight)
            .background(BazzarTheme.colors.white),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.s)

    ) {
        BazzarAppBar(
            title = stringResource(id = R.string.edit_account),
            onNavigationClick = {
                onSendEvent(EditProfileContract.Event.OnBackClicked)
            },
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                EditProfileDataEntry(
                    modifier = Modifier
                        .background(color = BazzarTheme.colors.backgroundColor)
                        .padding(
                        horizontal = BazzarTheme.spacing.spacerMini,
                        vertical = BazzarTheme.spacing.l
                    ),
                    fullName = state.fullName.orEmpty(),
                    email = state.email.orEmpty(),
                    phone = state.phoneNumber.orEmpty(),
                    onEmailChanged = {
                        onSendEvent(EditProfileContract.Event.OnEmailChanged(it))
                    },
                    onNameChanged = {
                        onSendEvent(EditProfileContract.Event.OnNameChanged(it))
                    },
                    onEditProfileClick = {
                        onSendEvent(EditProfileContract.Event.OnEditAccountClicked)
                    },
                    onChangePasswordClick = {
                        onSendEvent(EditProfileContract.Event.OnChangePasswordClicked)
                    }
                )
            }
        }
    }
}