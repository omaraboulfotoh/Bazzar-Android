package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SearchProductRequest(
    @Json(name = "categoryId") val categoryId: Int? = null,
    @Json(name = "brandList") val brandList: ArrayList<Int> = arrayListOf(),
    @Json(name = "colorList") val colorList: ArrayList<Int> = arrayListOf(),
    @Json(name = "sizeList") val sizeList: ArrayList<Int> = arrayListOf(),
    @Json(name = "minPrice") val minPrice: Int? = null,
    @Json(name = "maxPrice") val maxPrice: Int? = null,
    @Json(name = "searchKey") val searchKey: String? = null,
    @Json(name = "sorting") val sorting: String? = null,
    @Json(name = "pageSize") val pageSize: Int? = null,
    @Json(name = "pageIndex") val pageIndex: Int? = null
) : Parcelable
