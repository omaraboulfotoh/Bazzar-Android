package com.bazzar.android.presentation.locationScreen.composables

import android.location.Address
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bazzar.android.presentation.theme.BazzarTheme

@Composable
fun LocationInfo(address: Address? = null) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = BazzarTheme.spacing.m)
            .padding(top = BazzarTheme.spacing.xs)
            .background(BazzarTheme.colors.white),
        colors = CardDefaults.cardColors(
            containerColor = BazzarTheme.colors.white
        ),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = BazzarTheme.spacing.m, vertical = BazzarTheme.spacing.l),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Delivery Location",
                style = BazzarTheme.typography.captionBold,
                color = BazzarTheme.colors.primaryButtonColor,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text =
                if (address != null) address.thoroughfare + ", " + address.subLocality + ", " + address.countryName
                else "",
                style = BazzarTheme.typography.subtitle1,
                color = BazzarTheme.colors.primaryButtonColor,
                textAlign = TextAlign.Center
            )
        }
    }
}