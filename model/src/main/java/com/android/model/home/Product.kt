package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "displayOrder")
    val displayOrder: Int? = null,
    @Json(name = "imagePath")
    val imagePath: String? = null,
    @Json(name = "brandId")
    val brandId: Int? = null,
    @Json(name = "brandTitle")
    val brandTitle: String? = null,
    @Json(name = "categoryId")
    val categoryId: Int? = null,
    @Json(name = "categoryTitle")
    val categoryTitle: String? = null,
    @Json(name = "isNew")
    val isNew: Boolean? = null,
    @Json(name = "isExclusive")
    val isExclusive: Boolean? = null,
    @Json(name = "price")
    val price: Double = 0.0,
    @Json(name = "oldPrice")
    val oldPrice: Double? = null,
    @Json(name = "discountPercentage")
    val discountPercentage: Double? = null,
) : Parcelable