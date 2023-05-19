package com.android.model.request

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class UserLoginRequest(
    @Json(name = "phone") val phone: String,
    @Json(name = "password") val password: String
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class ChangePasswordRequest(
    @Json(name = "oldPassword") val oldPassword: String,
    @Json(name = "newPassword") val newPassword: String
) : Parcelable