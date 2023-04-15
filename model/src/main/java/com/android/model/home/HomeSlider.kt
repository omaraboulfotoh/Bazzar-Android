package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class HomeSlider(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "displayOrder")
    val displayOrder: Int? = null,
    @Json(name = "imagePath")
    val imagePath: String? = null,
    @Json(name = "itemId")
    val itemId: Int? = null,
    @Json(name = "brandId")
    val brandId: Int? = null,
    @Json(name = "categoryId")
    val categoryId: Int? = null,
    @Json(name = "url")
    val url: String? = null,
) : Parcelable
