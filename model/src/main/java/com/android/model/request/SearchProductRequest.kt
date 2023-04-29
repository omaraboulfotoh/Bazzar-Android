package com.android.model.request

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SearchProductRequest(
    @Json(name = "categoryId") val categoryId: Int? = null,
    @Json(name = "brandList") val brandList: List<Int> = emptyList(),
    @Json(name = "searchKey") val searchKey: String? = null,
    @Json(name = "pageSize") val pageSize: Int = 20,
    @Json(name = "pageIndex") val pageIndex: Int = 0,
    @Json(name = "maxPrice") val maxPrice: Int = 0,
    @Json(name = "minPrice") val minPrice: Int = 0,
    @Json(name = "sizeList") val sizeList: List<Int> = emptyList(),
    @Json(name = "colorList") val colorList: List<Int> = emptyList(),
) : Parcelable
