package com.bazzar.android.presentation.createOrder.composables

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bazzar.android.R
import com.bazzar.android.common.orFalse
import com.bazzar.android.common.toPriceFormat
import com.bazzar.android.presentation.checkOutScreen.composables.AddressItem
import com.bazzar.android.presentation.composables.BazzarAppBar
import com.bazzar.android.presentation.composables.PrimaryButton
import com.bazzar.android.presentation.composables.RemoteImage
import com.bazzar.android.presentation.composables.Subtitle
import com.bazzar.android.presentation.composables.Title
import com.bazzar.android.presentation.composables.bottomNavigation.BottomNavigationHeight
import com.bazzar.android.presentation.createOrder.CreateOrderContract.Event
import com.bazzar.android.presentation.createOrder.CreateOrderContract.State
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun CreateOrderScreenContent(
    state: State,
    onSendEvent: (Event) -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BazzarTheme.colors.backgroundColor)
            .padding(bottom = BottomNavigationHeight)
    ) {

        // top bar
        BazzarAppBar(title = stringResource(id = R.string.order_summry),
            onNavigationClick = { onSendEvent(Event.OnBackClicked) })

        // pricing view
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .background(BazzarTheme.colors.backgroundColor),
                verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.m),
                horizontalAlignment = Alignment.Start
            ) {

                // invoice details
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BazzarTheme.colors.white)
                        .padding(BazzarTheme.spacing.m),
                    verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.s),
                    horizontalAlignment = Alignment.Start
                ) {
                    Subtitle(
                        modifier = Modifier.wrapContentSize(),
                        text = stringResource(id = R.string.invoice_details),
                        style = BazzarTheme.typography.body2Bold,
                        color = BazzarTheme.colors.primaryButtonColor
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Title(
                            modifier = Modifier.wrapContentSize(),
                            text = stringResource(id = R.string.sub_total),
                            style = BazzarTheme.typography.body2Medium,
                            color = BazzarTheme.colors.primaryButtonColor
                        )
                        Title(
                            modifier = Modifier.wrapContentSize(),
                            text = "${state.subTotal.toPriceFormat()} KD",
                            style = BazzarTheme.typography.body2Medium,
                            color = BazzarTheme.colors.primaryButtonColor
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Title(
                            modifier = Modifier.wrapContentSize(),
                            text = stringResource(id = R.string.shipping),
                            style = BazzarTheme.typography.body2Medium,
                            color = BazzarTheme.colors.primaryButtonColor
                        )
                        Title(
                            modifier = Modifier.wrapContentSize(),
                            text = "${state.shipping.toPriceFormat()} KD",
                            style = BazzarTheme.typography.body2Medium,
                            color = BazzarTheme.colors.primaryButtonColor
                        )
                    }
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp),
                        color = BazzarTheme.colors.stroke
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Title(
                            modifier = Modifier.wrapContentSize(),
                            text = stringResource(id = R.string.total),
                            style = BazzarTheme.typography.body2Bold,
                            color = BazzarTheme.colors.primaryButtonColor
                        )
                        Title(
                            modifier = Modifier.wrapContentSize(),
                            text = "${state.totalPrice.toPriceFormat()} KD",
                            style = BazzarTheme.typography.body2Bold,
                            color = BazzarTheme.colors.primaryButtonColor
                        )
                    }
                }
                // payment Methods
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BazzarTheme.colors.white)
                        .padding(BazzarTheme.spacing.m),
                    verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.s),
                    horizontalAlignment = Alignment.Start
                ) {

                    Subtitle(
                        modifier = Modifier.wrapContentSize(),
                        text = stringResource(id = R.string.payment_method),
                        style = BazzarTheme.typography.body2Bold,
                        color = BazzarTheme.colors.primaryButtonColor
                    )
                    state.paymentMethodList.orEmpty().forEachIndexed { index, paymentMethod ->

                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .clickable {
                                    onSendEvent(Event.OnPaymentMethodClicked(index))
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs)
                        ) {

                            RadioButton(selected = paymentMethod.isSelected.orFalse(), onClick = {
                                onSendEvent(Event.OnPaymentMethodClicked(index))
                            })
                            RemoteImage(
                                imageUrl = paymentMethod.imageUrl,
                                modifier = Modifier.size(24.dp),
                                background = BazzarTheme.colors.white,
                                contentScale = ContentScale.FillBounds
                            )
                            Subtitle(
                                modifier = Modifier.wrapContentSize(),
                                text = paymentMethod.title.orEmpty(),
                                style = BazzarTheme.typography.body2Medium,
                                color = BazzarTheme.colors.primaryButtonColor
                            )
                        }
                    }
                }

                // address section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BazzarTheme.colors.white)
                        .padding(BazzarTheme.spacing.m),
                    verticalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.s),
                    horizontalAlignment = Alignment.Start
                ) {
                    Subtitle(
                        text = stringResource(id = R.string.shipping_address),
                        style = BazzarTheme.typography.body2Bold,
                        color = BazzarTheme.colors.primaryButtonColor
                    )
                    state.address?.let {
                        AddressItem(
                            address = it,
                            onSetAsDefaultClick = {},
                            onEditAddressClick = {},
                            onDeleteAddress = { }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(BazzarTheme.spacing.xl))
            }
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = BazzarTheme.spacing.l)
                    .height(65.dp)
                    .align(Alignment.BottomCenter),
                text = stringResource(id = R.string.place_order),
                textColor = colorResource(id = R.color.white),
                onClick = { onSendEvent(Event.OnCreateOrderClicked) })
        }
    }

}