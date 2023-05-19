package com.bazzar.android.presentation.checkOutScreen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.checkOutScreen.CheckOutContract
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.PrimaryButton
import com.bazzar.android.presentation.composables.PrimaryOutlinedButton
import com.bazzar.android.presentation.composables.SectionTitle
import com.bazzar.android.presentation.composables.Subtitle
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun CheckOutScreenContent(
    state: CheckOutContract.State,
    onSendEvent: (CheckOutContract.Event) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor)
            .padding(bottom = BottomNavigationHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
    ) {

        // title
        BazzarAppBar(title = stringResource(id = R.string.shipping_address), onNavigationClick = {
            onSendEvent(CheckOutContract.Event.OnBackClicked)

        })

        // show address if invalid else show empty view
        state.selectedAddress?.let {
            AddressView(selectedAddress = state.selectedAddress,
                onChangeAddressClicked = {
                    onSendEvent(CheckOutContract.Event.OnChangeAddressClicked)
                }
            )
        } ?: kotlin.run {
            Spacer(modifier = Modifier.height(BazzarTheme.spacing.m))
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_empty_address),
                contentDescription = "ic_empty_address"
            )
            SectionTitle(
                modifier = Modifier.wrapContentSize(),
                text = stringResource(id = R.string.you_dont_have_address),
                textAlign = TextAlign.Center,
                color = BazzarTheme.colors.primaryButtonColor
            )
        }


        // add new address button
        Spacer(modifier = Modifier.height(BazzarTheme.spacing.m))
        PrimaryOutlinedButton(
            text = stringResource(id = R.string.add_new_address),
            onClick = { onSendEvent(CheckOutContract.Event.OnAddNewAddressClicked) },
            color = colorResource(id = R.color.prussian_blue),
            modifier = Modifier
                .height(65.dp)
                .fillMaxWidth()
                .padding(horizontal = BazzarTheme.spacing.m)
        )


        Spacer(modifier = Modifier.weight(1f))

        // checkout button
        PrimaryButton(
            text = stringResource(id = R.string.continue_to_pay),
            onClick = { onSendEvent(CheckOutContract.Event.OnContinueClicked) },
            textColor = Color.White,
            enabled = state.selectedAddress != null,
            modifier = Modifier
                .padding(horizontal = BazzarTheme.spacing.m)
                .height(65.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(1.dp))
    }
}
