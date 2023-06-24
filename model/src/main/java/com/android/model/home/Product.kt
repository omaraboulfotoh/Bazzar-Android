package com.android.model.home

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "cartId") val cartId: Int? = null,
    @Json(name = "itemId") val itemId: Int? = null,
    @Json(name = "itemDetailId") val itemDetailId: Int? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "itemTitle") val itemTitle: String? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "marketerName") val marketerName: String? = null,
    @Json(name = "displayOrder") val displayOrder: Int? = null,
    @Json(name = "imagePath") val imagePath: String? = null,
    @Json(name = "brandId") val brandId: Int? = null,
    @Json(name = "brandTitle") val brandTitle: String? = null,
    @Json(name = "categoryId") val categoryId: Int? = null,
    @Json(name = "categoryTitle") val categoryTitle: String? = null,
    @Json(name = "isNew") val isNew: Boolean? = false,
    @Json(name = "isSoldOut") val isSoldOut: Boolean? = false,
    @Json(name = "isWishList") val isWishList: Boolean? = false,
    @Json(name = "isExclusive") val isExclusive: Boolean? = null,
    @Json(name = "price") val price: Double? = 0.0,
    @Json(name = "oldPrice") val oldPrice: Double? = null,
    @Json(name = "discountPercentage") val discountPercentage: Int? = null,
    @Json(name = "itemDetails") val itemDetails: List<ItemDetail>? = listOf(),
    @Json(name = "itemImages") val itemImages: List<ItemImages>? = listOf(),
    @Json(name = "relatedItems") val relatedItems: List<Product>? = listOf(),
    @Json(name = "categoryImagePath") val categoryImagePath: String? = null,
    @Json(name = "brandImagePath") val brandImagePath: String? = null,
    @Json(name = "selectedItemDetails") val selectedItemDetails: ItemDetail? = null,
    @Json(name = "shareURL") val shareURL: String? = null,
    @Json(name = "colorId") val colorId: Int? = null,
    @Json(name = "colorTitle") val colorTitle: String? = null,
    @Json(name = "sizeId") val sizeId: Int? = null,
    @Json(name = "sizeTitle") val sizeTitle: String? = null,
    @Json(name = "isAvailable") val isAvailable: Boolean? = true,
    @Json(name = "itemBalance") val itemBalance: Int? = null,
    @Json(name = "qty") val qty: Int? = null,
) : Parcelable {
    fun getProductId() = id ?: itemId
}


@Parcelize
@JsonClass(generateAdapter = true)
data class ItemDetail(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "colorId") val colorId: Int? = null,
    @Json(name = "colorTitle") val colorTitle: String? = null,
    @Json(name = "sizeId") val sizeId: Int? = null,
    @Json(name = "sizeTitle") val sizeTitle: String? = null,
    @Json(name = "price") val price: Double? = null,
    @Json(name = "discountPercentage") val discountPercentage: Double? = null,
    @Json(name = "oldPrice") val oldPrice: Double? = null,
    @Json(name = "sku") val sku: String? = null,
    @Json(name = "itemBalance") val itemBalance: Int? = null,
    @Json(name = "quantity") val quantity: Int? = null,
    val isSelected: Boolean? = false
) : Parcelable


@Parcelize
@JsonClass(generateAdapter = true)
data class ItemImages(
    @Json(name = "colorId") val colorId: Int? = null,
    @Json(name = "imagePath") val imagePath: String? = null,
    @Json(name = "colorTitle") val colorTitle: String? = null
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class PaymentMethod(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "imageUrl") val imageUrl: String? = null,
    @Json(name = "title") val title: String? = null,
    val isSelected: Boolean? = false,
) : Parcelable
