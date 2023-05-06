package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class OrderHistory(
    val id: Int = 0,
    val orderDate: String = "",
    val orderNumber: String,
    val subTotal: Double? = null,
    val deliveryCharges: Double? = null,
    val discount: String? = null,
    val totalPrice: Double? = null,
    val currentStatus: String? = null,
    val orderNotes: String? = null,
    val paymentURL: String? = null,
    val customer: String? = null,
    val address: OrderAddress? = null,
    val items: List<OrderItem>? = emptyList(),
    val statusLog: List<StatusLog>? = emptyList()
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class OrderAddress(
    val id: Int? = null,
    val areaId: Int? = null,
    val streetName: String? = null,
    val houseNumber: String? = null,
    val flatNumber: String? = null,
    val addressDescription: String? = null,
    val addressNotes: String? = null,
    val isDefault: Boolean? = false,
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class OrderItem(
    val itemDetailId: Int? = null,
    val itemId: Int? = null,
    val categoryTitle: String? = "",
    val brandTitle: String? = "",
    val itemTitle: String? = "",
    val colorTitle: String? = "",
    val sizeTitle: String? = "",
    val imagePath: String? = "",
    val price: Double? = null,
    val qty: Int? = null,
    val sku: String? = null,
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class StatusLog(
    val id: Int,
    val title: String? = "",
    val isSelected: Boolean? = false
) : Parcelable