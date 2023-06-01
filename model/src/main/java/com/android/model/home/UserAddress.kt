package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class UserAddress(
    @Json(name = "userId")
    var userId: Int? = null,
    @Json(name = "id")
    var id: Int? = null,
    @Json(name = "areaId")
    var areaId: Int? = null,
    @Json(name = "governorateId")
    var governorateId: Int? = null,
    @Json(name = "streetName")
    var streetName: String? = null,
    @Json(name = "block")
    var block: String? = null,
    @Json(name = "houseNumber")
    var houseNumber: String? = null,
    @Json(name = "flatNumber")
    var flatNumber: String? = null,
    @Json(name = "jada")
    var jada: String? = null,
    @Json(name = "addressDescription")
    var addressDescription: String? = null,
    @Json(name = "addressNotes")
    var addressNotes: String? = null,
    @Json(name = "latitude")
    var latitude: String? = null,
    @Json(name = "longitude")
    var longitude: String? = null,
    @Json(name = "isDefault")
    var isDefault: Boolean? = null,
    @Json(name = "isDeleted")
    var isDeleted: Boolean? = null,
) : Parcelable
