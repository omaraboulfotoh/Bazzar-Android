package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CreateOrderModel(
    @Json(name = "orderNumber")
    val orderNumber: Int? = null,
    @Json(name = "paymentURL")
    val paymentURL: String? = null,
    @Json(name = "orderDate")
    val orderDate: String? = null,
) : Parcelable