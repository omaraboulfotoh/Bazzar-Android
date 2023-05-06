package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Area(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "parentId")
    val parentId: Int? = null,
    @Json(name = "title")
    val title: String? = "",
    @Json(name = "deliveryCost")
    val deliveryCost: String? = null
) : Parcelable
