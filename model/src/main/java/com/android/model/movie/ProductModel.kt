package com.android.model.movie

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal

@Parcelize
@JsonClass(generateAdapter = true)
data class ProductModel(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "brand_name")
    val brandName: String? = null,
    @Json(name = "product_title")
    val productTitle: String? = null,
    @Json(name = "price_before_sale")
    val priceBeforeSale: Double = 0.0,
    @Json(name = "price_after_sale")
    val priceAfterSale: Double? = null,
    @Json(name = "poster_path")
    val poster: String? = null,
    @Json(name = "local_poster_path")
    val localPoster: Int? = null,
) : Parcelable