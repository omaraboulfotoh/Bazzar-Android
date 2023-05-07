package com.android.model.request

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class LoadCheckoutRequest(
    @Json(name = "userAddressId")
    val userAddressId: Int = 0,
    @Json(name = "paymentMethodId")
    val paymentMethodId: Int? = null,
    @Json(name = "cartItems")
    val cartItems: List<CartItemRequest>,
    @Json(name = "orderNotes")
    val orderNotes: String = "",
) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class CartItemRequest(
    @Json(name = "itemDetailId")
    val itemDetailId: Int,
    @Json(name = "qty")
    val qty: Int,
) : Parcelable