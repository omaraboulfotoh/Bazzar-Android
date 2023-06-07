package com.bazzar.android.presentation.checkOutScreen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.model.home.Area
import com.android.model.home.UserAddress
import com.bazzar.android.R
import com.bazzar.android.presentation.composables.DiscountStylesInText
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun AddressItem(
    address: UserAddress,
    areasList: List<Area>,
    showActions: Boolean = true,
    onSetAsDefaultClick: () -> Unit,
    onEditAddressClick: () -> Unit,
    onDeleteAddress: () -> Unit,
) {

    val area = areasList.first { it.id == address.areaId }
    val government = areasList.first { it.id == area.parentId }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = BazzarTheme.colors.white,
        shape = RoundedCornerShape(22.dp),
        elevation = 4.dp,
    ) {
        Column(
            modifier = Modifier.padding(BazzarTheme.spacing.m),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "${area.title.orEmpty()}, ${government.title.orEmpty()}",
                    style = BazzarTheme.typography.body2Bold,
                    fontSize = 14.sp,
                )
                if (showActions)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .padding(end = BazzarTheme.spacing.m)
                                .clickable { onEditAddressClick.invoke() },
                            imageVector = ImageVector.vectorResource(id = R.drawable.icon_awesome_pen),
                            contentDescription = null,
                        )
                        Image(
                            modifier = Modifier.clickable { onDeleteAddress.invoke() },
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_trash),
                            contentDescription = null,
                        )
                    }
            }
            Divider(
                modifier = Modifier
                    .padding(top = BazzarTheme.spacing.xs)
                    .fillMaxWidth()
                    .height(1.dp)
            )
            DiscountStylesInText(
                modifier = Modifier.padding(top = BazzarTheme.spacing.m),
                text1 = "${stringResource(id = R.string.address_flat)}: ${address.flatNumber}, " +
                        "${stringResource(id = R.string.address_building)}: ${address.houseNumber}, " +
                        "${stringResource(id = R.string.address_street)}: ${address.streetName}, " +
                        "${stringResource(id = R.string.jaddah)}: ${address.addressDescription}, " +
                        "${stringResource(id = R.string.area)}: ${area.title.orEmpty()}, ",
                text2 = government.title.orEmpty()
            )
            Divider(
                modifier = Modifier
                    .padding(top = BazzarTheme.spacing.xs)
                    .fillMaxWidth()
                    .height(1.dp)
            )
            Row(
                modifier = Modifier
                    .padding(top = BazzarTheme.spacing.m)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(id = R.string.set_default_address),
                    style = MaterialTheme.typography.subtitle2.copy(
                        color = colorResource(id = R.color.black),
                        fontFamily = FontFamily(Font(R.font.siwa_heavy))
                    )
                )
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colorResource(id = if (address.isDefault == true) R.color.deep_sky_blue else R.color.light_gray))
                        .clickable {
                            if (address.isDefault != null) onSetAsDefaultClick.invoke()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.toggle_circle),
                        contentDescription = null,
                        tint = colorResource(id = if (address.isDefault == true) R.color.prussian_blue else R.color.dark_gray),
                        modifier = if (address.isDefault != true) Modifier
                            .align(Alignment.CenterStart)
                            .padding(2.dp) else Modifier
                            .align(Alignment.CenterEnd)
                            .padding(2.dp)
                    )
                }
            }
        }
    }
}