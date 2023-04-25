package com.bazzar.android.presentation.cartScreen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.bazzar.android.presentation.composables.PrimaryButton
import com.bazzar.android.presentation.theme.BazzarTheme


@Composable
fun CartScreenContent(
    state: CartContract.State, onSendEvent: (CartContract.Event) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
    ) {
        // header item
        CartSummary(state.counterItem)

        // rest of floating design
        Box(
            modifier = Modifier
                .weight(1f)
        )
        {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                state.productCartList?.let {
                    itemsIndexed(it) { index, product ->
                        ProductCartItem(product)
                    }
                }
            }
            PrimaryButton(
                text = stringResource(id = R.string.check_out),
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(123.dp)
                    .height(65.dp)
                    .clip(RoundedCornerShape(32.5.dp))
                    .background(colorResource(id = R.color.prussian_blue)),
                textColor = colorResource(id = R.color.white)
            )
        }
    }
}


