package com.bazzar.android.presentation.ordersHistory.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.model.order.OrderHistory
import com.android.model.order.OrderItem
import com.android.model.order.StatusLog
import com.bazzar.android.R
import com.bazzar.android.common.fromBasicServerToFullDateFormat
import com.bazzar.android.presentation.composables.RemoteImage
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun OrderHistoryItem(
    modifier: Modifier = Modifier,
    orderHistory: OrderHistory
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = BazzarTheme.colors.white),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(modifier = Modifier.padding(vertical = 10.dp)) {
            ItemImages(items = orderHistory.items.orEmpty())
            OrderNumberAndPrice(
                orderNumber = orderHistory.orderNumber,
                totalPrice = "${orderHistory.totalPrice}"
            )
            OrderStatus(orderStatus = orderHistory.statusLog.orEmpty())
            OrderFooter(date = orderHistory.orderDate)
        }
    }
}

@Composable
private fun ItemImages(items: List<OrderItem>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEach {
            Box(
                modifier = Modifier
                    .padding(horizontal = BazzarTheme.spacing.xxs)
                    .background(color = BazzarTheme.colors.white, shape = RoundedCornerShape(6.dp))
                    .border(
                        width = 1.dp,
                        color = BazzarTheme.colors.stroke,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(4.dp)
            ) {
                RemoteImage(
                    modifier = Modifier.size(28.dp),
                    imageUrl = it.imagePath
                )
            }
        }
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                        fontSize = 14.sp,
                    )
                ) {
                    append(stringResource(id = R.string.qty))
                }
                withStyle(
                    style = SpanStyle(
                        fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                        fontSize = 14.sp,
                    )
                ) {
                    append(":x${items.size}")
                }
            }
        )
    }
}

@Composable
private fun OrderNumberAndPrice(
    orderNumber: String,
    totalPrice: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(top = 28.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.order_number),
                style = BazzarTheme.typography.body2,
                fontSize = 14.sp,
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = orderNumber,
                style = BazzarTheme.typography.body2Bold,
                fontSize = 14.sp,
            )
        }
        Divider(
            modifier = Modifier
                .background(color = BazzarTheme.colors.black)
                .width(1.dp)
                .height(34.dp)
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.order_total),
                style = BazzarTheme.typography.body2,
                fontSize = 14.sp,
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                            fontSize = 14.sp,
                        )
                    ) {
                        append(totalPrice)
                    }
                    withStyle(
                        style = SpanStyle(
                            fontFamily = FontFamily(Font(R.font.montserrat_regular)),
                            fontSize = 14.sp,
                        )
                    ) {
                        append(" kd")
                    }
                },
            )
        }
    }
}

@Composable
private fun OrderStatus(orderStatus: List<StatusLog>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 28.dp),
        contentPadding = PaddingValues(horizontal = BazzarTheme.spacing.m),
        horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs)
    ) {
        itemsIndexed(orderStatus) { index, item ->
            Box(
                modifier = Modifier.border(
                    width = 1.dp,
                    color = if (item.isSelected == true) BazzarTheme.colors.primaryButtonColor else BazzarTheme.colors.stroke,
                    shape = RoundedCornerShape(6.dp)
                )
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 14.dp, horizontal = 10.dp),
                    style = BazzarTheme.typography.overlineBold,
                    text = item.title ?: "",
                    color = if (item.isSelected == true) BazzarTheme.colors.primaryButtonColor else BazzarTheme.colors.textGray
                )
            }
        }
    }
}

@Composable
private fun OrderFooter(date: String) {
    Column(
        modifier = Modifier.padding(
            start = BazzarTheme.spacing.m,
            end = BazzarTheme.spacing.xs
        )
    ) {
        Divider(
            modifier = Modifier
                .padding(vertical = BazzarTheme.spacing.m)
                .fillMaxWidth()
                .height(1.dp)
                .background(BazzarTheme.colors.borderColor)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = date.fromBasicServerToFullDateFormat() ?: "", style = BazzarTheme.typography.overlineBold)

//            TextButton(modifier = Modifier
//                .background(
//                    color = BazzarTheme.colors.primaryButtonColor,
//                    shape = RoundedCornerShape(34.dp)
//                )
//                .border(
//                    width = 1.dp,
//                    color = BazzarTheme.colors.dodgerBlue,
//                    shape = RoundedCornerShape(34.dp)
//                ), shape = RoundedCornerShape(14.dp), onClick = { }) {
//                Text(
//                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp),
//                    text = "Order Details",
//                    color = BazzarTheme.colors.dodgerBlue,
//                    style = BazzarTheme.typography.overlineBold
//                )
//            }
        }
    }
}