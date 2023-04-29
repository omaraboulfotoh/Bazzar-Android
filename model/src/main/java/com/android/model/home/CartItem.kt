package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CartItem(
    @Json(name = "itemDetailId")
    var itemDetailId: Int?,
    @Json(name = "qty")
    var qty: Int?
) : Parcelable
