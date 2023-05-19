package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SortFilter(
    @Json(name = "sortingList")
    val sortingList: List<SortItem>? = null,
    @Json(name = "categoryList")
    val categoryList: List<FilterItem>? = null,
    @Json(name = "brandList")
    val brandList: List<SortItem>? = null,
    @Json(name = "colorList")
    val colorList: List<SortItem>? = null,
    @Json(name = "sizeList")
    val sizeList: List<SortItem>? = null,
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class SortItem(
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "sortKey")
    val sortKey: String? = null,
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class FilterItem(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "title")
    val title: String? = null,
) : Parcelable
