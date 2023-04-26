package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class UserAddress(
    @Json(name = "id")
    var id: Int? = null,
    @Json(name = "areaId")
    var areaId: Int? = null,
    @Json(name = "streetName")
    var streetName: String? = null,
    @Json(name = "houseNumber")
    var houseNumber: String? = null,
    @Json(name = "flatNumber")
    var flatNumber: String? = null,
    @Json(name = "addressDescription")
    var addressDescription: String? = null,
    @Json(name = "addressNotes")
    var addressNotes: String? = null,
    @Json(name = "isDefault")
    var isDefault: Boolean? = null
) : Parcelable
