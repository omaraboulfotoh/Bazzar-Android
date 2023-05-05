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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.RemoteImage
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun OrderHistoryItem(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(modifier = Modifier.padding(vertical = 10.dp)) {
            ItemImages()
            OrderNumberAndPrice()
            OrderActions()
            OrderFooter()
        }
    }
}

@Composable
private fun ItemImages() {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp)
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
                imageUrl = "https://demo.bazzargate.com/image?ss=55A1F566-4628-4E32-9777-968E5B0689D2&w=200&h=0&r=1"
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp)
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
                imageUrl = "https://demo.bazzargate.com/image?ss=55A1F566-4628-4E32-9777-968E5B0689D2&w=200&h=0&r=1"
            )
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 4.dp)
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
                imageUrl = "https://demo.bazzargate.com/image?ss=55A1F566-4628-4E32-9777-968E5B0689D2&w=200&h=0&r=1"
            )
        }
    }
}

@Composable
private fun OrderNumberAndPrice() {
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
                text = "Order Number",
                style = BazzarTheme.typography.body2,
                fontSize = 14.sp,
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = "1000256321",
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
                text = "Order Total",
                style = BazzarTheme.typography.body2,
                fontSize = 14.sp,
            )
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = "000.000 KW",
                style = BazzarTheme.typography.body2Bold,
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
private fun OrderActions() {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 28.dp),
        contentPadding = PaddingValues(horizontal = BazzarTheme.spacing.m),
        horizontalArrangement = Arrangement.spacedBy(BazzarTheme.spacing.xs)
    ) {
        itemsIndexed(
            listOf(
                "Order Placed", "Preparing Order", "Out For Delivery", "Foods"
            )
        ) { index, item ->
            TextButton(modifier = Modifier.border(
                width = 1.dp,
                color = if (index == 0) BazzarTheme.colors.primaryButtonColor else BazzarTheme.colors.borderColor,
                shape = RoundedCornerShape(6.dp)
            ), shape = RoundedCornerShape(6.dp), onClick = { /*TODO*/ }) {
                Text(
                    modifier = Modifier.padding(vertical = 14.dp, horizontal = 10.dp),
                    style = BazzarTheme.typography.overlineBold,
                    text = item,
                    color = if (index == 0) BazzarTheme.colors.primaryButtonColor else BazzarTheme.colors.textGray
                )
            }
        }
    }
}

@Composable
private fun OrderFooter() {
    Column(modifier = Modifier.padding(start = 16.dp, end = 8.dp)) {
        Divider(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(BazzarTheme.colors.borderColor)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "22/2/2023   02:30 pm", style = BazzarTheme.typography.overlineBold
            )

            TextButton(modifier = Modifier
                .background(
                    color = BazzarTheme.colors.primaryButtonColor,
                    shape = RoundedCornerShape(34.dp)
                )
                .border(
                    width = 1.dp,
                    color = BazzarTheme.colors.dodgerBlue,
                    shape = RoundedCornerShape(34.dp)
                ), shape = RoundedCornerShape(14.dp), onClick = { /*TODO*/ }) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp),
                    text = "Order Details",
                    color = BazzarTheme.colors.dodgerBlue,
                    style = BazzarTheme.typography.overlineBold
                )
            }
        }
    }
}