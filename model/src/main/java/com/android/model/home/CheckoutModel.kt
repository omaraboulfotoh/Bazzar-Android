package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CheckoutModel(
    val items: List<Product>? = null
) : Parcelable