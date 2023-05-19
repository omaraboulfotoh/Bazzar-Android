package com.bazzar.android.presentation.cartScreen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.presentation.cartScreen.CartContract
import com.bazzar.android.presentation.composables.EmptyMbcAppBar
import com.bazzar.android.presentation.composables.PrimaryButton
import com.bazzar.android.presentation.composables.PrimaryOutlinedButton
import com.bazzar.android.presentation.composables.ProductItem
import com.bazzar.android.presentation.composables.SectionTitle
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
            .padding(bottom = BottomNavigationHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
    ) {


        // top bar
        EmptyMbcAppBar(title = stringResource(id = R.string.cart))

        Spacer(modifier = Modifier.height(BazzarTheme.spacing.xxs))

        if (state.showEmptyCart.not()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = BazzarTheme.spacing.m)
                    .padding(bottom = BazzarTheme.spacing.xs),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m)
            ) {
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
                                ProductCartItem(
                                    modifier = Modifier.padding(vertical = BazzarTheme.spacing.xs),
                                    product = product,
                                    onItemClicked = {
                                        onSendEvent(CartContract.Event.OnProductClicked(index))
                                    }, onDeleteClicked = {
                                        onSendEvent(CartContract.Event.OnDeleteItem(index))
                                    }, onMinusClicked = {
                                        onSendEvent(CartContract.Event.OnMinusItem(index))
                                    }, onPlusClicked = {
                                        onSendEvent(CartContract.Event.OnPlusItem(index))
                                    })
                            }
                        }
                    }

                    // checkout button
                    if (state.productCartList.isNullOrEmpty().not())
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
            }
        } else {
            Spacer(modifier = Modifier.height(BazzarTheme.spacing.m))
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_empty_cart),
                contentDescription = "ic_empty_cart"
            )
            SectionTitle(
                modifier = Modifier.alpha(0.5f),
                textAlign = TextAlign.Center,
                color = BazzarTheme.colors.primaryButtonColor,
                text = stringResource(id = R.string.empty_cart)
            )
            Spacer(modifier = Modifier.height(1.dp))
            PrimaryOutlinedButton(
                modifier = Modifier.width(176.dp),
                text = stringResource(id = R.string.start_shopping_now),
                color = BazzarTheme.colors.primaryButtonColor,
                style = BazzarTheme.typography.captionBold,
                onClick = { onSendEvent(CartContract.Event.OnShoppingClicked) })

            if (state.showWishList) {
                Spacer(modifier = Modifier.height(1.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .background(BazzarTheme.colors.white)
                ) {
                    SectionTitle(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.add_from_your_wish_list)
                    )

                }
                LazyRow(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs),
                    contentPadding = PaddingValues(horizontal = BazzarTheme.spacing.xs),
                ) {

                    itemsIndexed(state.productWishList.orEmpty()) { index, product ->
                        ProductItem(
                            product,
                            onItemClicked = { onSendEvent(CartContract.Event.OnProductClicked(index)) },
                            onFavClicked = {
                                onSendEvent(
                                    CartContract.Event.OnProductFavClicked(
                                        index
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(BazzarTheme.spacing.m))
    }
}


