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
data class GuestLoginRequest(
    @Json(name = "deviceId") val deviceId: String,
    @Json(name = "accessToken") val accessToken: String
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class ChangePasswordRequest(
    @Json(name = "oldPassword") val oldPassword: String,
    @Json(name = "newPassword") val newPassword: String
) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class ContactUsRequest(
    @Json(name = "name") val name: String,
    @Json(name = "email") val email: String,
    @Json(name = "phone") val phone: String,
    @Json(name = "subject") val subject: String,
    @Json(name = "message") val message: String,
) : Parcelable