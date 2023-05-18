package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Category(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "parentId")
    val parentId: Int? = null,
    @Json(name = "imagePath")
    val imagePath: String? = null,
    val isSelected: Boolean = false,
) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class ProductSection(
    @Json(name = "categoryId")
    val categoryId: Int? = null,
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "items")
    val items: List<Product>? = null
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class BazaarModel(
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

