package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class UserData(
    @Json(name = "accessToken")
    val accessToken: String? = null,
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "englishName")
    val englishName: String? = null,
    @Json(name = "email")
    val email: String? = null,
    @Json(name = "phone")
    val phone: String? = null
) : Parcelable

