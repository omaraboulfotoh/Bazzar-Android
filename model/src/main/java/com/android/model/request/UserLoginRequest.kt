package com.android.model.request

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class UserLoginRequest(val phone: String, val password: String) : Parcelable