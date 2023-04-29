package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Checkout(
    @Json(name = "userAddressId")
    var userAddressId: Int?,
    @Json(name = "cartItems")
    var cartItems: List<CartItem>?
) : Parcelable
