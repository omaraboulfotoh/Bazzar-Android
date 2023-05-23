package com.bazzar.android.presentation.locationScreen.composables

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bazzar.android.R
import com.bazzar.android.presentation.theme.BazzarTheme
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode


@Composable
fun LocationSearch(
    onLocationSelected: (Place) -> Unit
) {
    val context = LocalContext.current
    val resultLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val place = Autocomplete.getPlaceFromIntent(data)
            onLocationSelected(place)
        }
    }

    val intent = Autocomplete.IntentBuilder(
        AutocompleteActivityMode.OVERLAY,
        listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
    )
        .build(context)

    val onClick = {
        resultLauncher.launch(intent)
    }

    Row(
        modifier = Modifier
            .padding(horizontal = BazzarTheme.spacing.m)
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(color = BazzarTheme.colors.primaryButtonColor)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.padding(horizontal = BazzarTheme.spacing.s),
            painter = painterResource(id = R.drawable.search_icon),
            tint = BazzarTheme.colors.white,
            contentDescription = ""
        )
        Text(
            modifier = Modifier.padding(vertical = 8.dp),
            text = stringResource(id = R.string.search_location),
            style = BazzarTheme.typography.subtitle3,
            fontSize = 14.sp,
            color = colorResource(id = R.color.white)
        )
    }
}