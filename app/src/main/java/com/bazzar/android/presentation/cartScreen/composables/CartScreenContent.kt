package com.bazzar.android.presentation.cartScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.cartScreen.CartContract
import com.bazzar.android.presentation.composables.EmptyMbcAppBar
import com.bazzar.android.presentation.composables.PrimaryButton
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.theme.BazzarTheme


@Composable
fun CartScreenContent(
    state: CartContract.State, onSendEvent: (CartContract.Event) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor)
            .padding(horizontal = BazzarTheme.spacing.m)
            .padding(bottom = BottomNavigationHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
    ) {


        // top bar
        EmptyMbcAppBar(title = stringResource(id = R.string.cart))

        Spacer(modifier = Modifier.height(BazzarTheme.spacing.xxs))

        // header item
        CartSummary(state.counterItem, state.totalCartAMount)

        // rest of floating design
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 65.dp)
            ) {
                state.productCartList?.let {
                    itemsIndexed(it) { index, product ->
                        ProductCartItem(product)
                    }
                }
            }

            // checkout button
            PrimaryButton(
                text = stringResource(id = R.string.check_out),
                onClick = { onSendEvent(CartContract.Event.OnCheckout) },
                modifier = Modifier
                    .width(124.dp)
                    .height(65.dp)
                    .clip(RoundedCornerShape(32.5.dp))
                    .background(colorResource(id = R.color.prussian_blue))
                    .align(Alignment.BottomStart),
                textColor = colorResource(id = R.color.white)
            )
        }
        Spacer(modifier = Modifier.height(BazzarTheme.spacing.m))
    }
}


