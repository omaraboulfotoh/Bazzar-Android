package com.android.model.responses.base

import android.os.Parcelable
import com.android.model.home.Category
import com.android.model.home.HomeSlider
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class BazaarDetailsResponse(
    @Json(name = "slider")
    val slider: List<HomeSlider>? = emptyList(),
    @Json(name = "categoryList")
    val categoryList: List<Category>? = emptyList(),
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "imagePath")
    val imagePath: String? = null,
    @Json(name = "shareURL")
    val shareURL: String? = null,
    @Json(name = "isWishList")
    val isWishList: Boolean? = false,
) : Parcelable
