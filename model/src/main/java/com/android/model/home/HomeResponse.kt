package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class HomeResponse(
    @Json(name = "slider1")
    val slider1: List<HomeSlider>? = emptyList(),
    @Json(name = "slider2")
    val slider2: List<HomeSlider>? = emptyList(),
    @Json(name = "featuredBrands")
    val featuredBrands: List<Brand>? = emptyList(),
    @Json(name = "featuredCategories")
    val featuredCategories: List<Category>? = emptyList(),
    @Json(name = "categoryItems")
    val categoryItems: List<ProductSection>? = emptyList(),
    @Json(name = "featuredBzarz")
    val featuredBzarz: List<BzarzModel>? = emptyList(),
) : Parcelable