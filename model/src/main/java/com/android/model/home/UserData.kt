package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class UserData(
    @Json(name = "id")
    val id:Int?=null,
    @Json(name = "name")
    val name:String,
    @Json(name = "englishName")
    val englishName:String,
    @Json(name = "email")
    val email:String,
    @Json(name = "phone")
    val phone:String
) : Parcelable

