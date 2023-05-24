package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class CheckoutModel(
    @Json(name = "cartItems") val cartItems: List<ItemDetail>? = null,
    @Json(name = "userAddressId") val userAddressId: Int? = null,
    @Json(name = "subTotal") val subTotal: Double? = null,
    @Json(name = "shipping") val shipping: Double? = null,
    @Json(name = "discount") val discount: Double? = null,
    @Json(name = "totalPrice") val totalPrice: Double? = null,
    @Json(name = "allItemsAvailable") val allItemsAvailable: Boolean? = null,
    @Json(name = "shippingPromotionId") val shippingPromotionId: Int? = null,
    @Json(name = "discountPromotionId") val discountPromotionId: Int? = null,
    @Json(name = "freeShippingMinOrderValue") val freeShippingMinOrderValue: Double? = null,
    @Json(name = "shippingMessage") val shippingMessage: String? = null,
    @Json(name = "paymentMethods") val paymentMethods: List<PaymentMethod>? = null,
) : Parcelable