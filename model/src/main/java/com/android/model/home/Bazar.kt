package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Bazar(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "imagePath")
    val imagePath: String? = null,
) : Parcelable

fun Bazar.toCategory() = Category(id = id, title = name, imagePath = imagePath)

fun Bazar.toBrand() = Brand(id = id, title = name, imagePath = imagePath)
