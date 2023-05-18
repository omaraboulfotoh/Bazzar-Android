package com.android.model.request

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AddToCartRequest(
    @Json(name = "itemDetailId")
    val itemDetailId: Int = 0,
    @Json(name = "marketerId")
    val marketerId: Int? = null,
    @Json(name = "qty")
    val qty: Int = 1,
) : Parcelable
