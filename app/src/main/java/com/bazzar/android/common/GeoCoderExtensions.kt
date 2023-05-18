package com.bazzar.android.common

import android.location.Address
import android.location.Geocoder
import android.os.Build
import com.google.android.gms.maps.model.LatLng

/**
 * Note try to execute this func into a coroutine
 */
fun Geocoder.getFromLatLng(latLng: LatLng, onComplete: (address: Address?) -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getFromLocation(latLng.latitude, latLng.longitude, 1) {
            onComplete.invoke(it.firstOrNull())
        }
    } else {
        val mAddress = getFromLocation(latLng.latitude, latLng.longitude, 1)?.firstOrNull()
        onComplete(mAddress)
    }
}