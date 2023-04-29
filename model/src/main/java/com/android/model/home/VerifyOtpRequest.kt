package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class VerifyOtpRequest(
    @Json(name = "userId")
    var userId: Int,

    @Json(name = "OTP")
    val otp: String,

) : Parcelable
