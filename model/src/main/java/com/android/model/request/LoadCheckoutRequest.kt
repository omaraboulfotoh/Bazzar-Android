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
    @Json(name = "orderNotes")
    val orderNotes: String? = "",
    @Json(name = "promotionCode")
    val promotionCode: String? = null,
) : Parcelable