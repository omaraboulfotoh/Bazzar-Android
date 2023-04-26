package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SearchProductRequest(
    @Json(name = "categoryId") val categoryId: Int? = null,
    @Json(name = "brandList") val brandList: List<Int>? = null,
    @Json(name = "searchKey") val searchKey: String? = null,
    @Json(name = "pageSize") val pageSize: Int = 20,
    @Json(name = "pageIndex") val pageIndex: Int = 0
) : Parcelable
