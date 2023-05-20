package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SortFilter(
    @Json(name = "sortingList")
    val sortingList: List<Sort>? = null,
    @Json(name = "categoryList")
    val categoryList: List<Filter>? = null,
    @Json(name = "brandList")
    val brandList: List<Filter>? = null,
    @Json(name = "colorList")
    val colorList: List<Filter>? = null,
    @Json(name = "sizeList")
    val sizeList: List<Filter>? = null,
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Sort(
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "sortKey")
    val sortKey: String? = null,
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Filter(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "title")
    val title: String? = null,
    @Json(ignore = true)
    var isSelected: Boolean = false,
) : Parcelable
