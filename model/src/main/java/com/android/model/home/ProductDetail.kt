package com.android.model.home

import com.squareup.moshi.Json

data class ProductDetail(
    @Json(name = "itemDetails") var itemDetails: ArrayList<ItemDetail> = arrayListOf(),
    @Json(name = "itemImages") var itemImages: ArrayList<ItemImages> = arrayListOf(),
    @Json(name = "relatedItems") var relatedItems: ArrayList<RelatedItems> = arrayListOf(),
    @Json(name = "description") var description: String? = null,
    @Json(name = "id") var id: Int? = null,
    @Json(name = "brandId") var brandId: Int? = null,
    @Json(name = "categoryId") var categoryId: Int? = null,
    @Json(name = "categoryTitle") var categoryTitle: String? = null,
    @Json(name = "categoryImagePath") var categoryImagePath: String? = null,
    @Json(name = "brandTitle") var brandTitle: String? = null,
    @Json(name = "brandImagePath") var brandImagePath: String? = null,
    @Json(name = "title") var title: String? = null,
    @Json(name = "displayOrder") var displayOrder: Int? = null,
    @Json(name = "imagePath") var imagePath: String? = null,
    @Json(name = "price") var price: Double? = null,
    @Json(name = "discountPercentage") var discountPercentage: Double? = null,
    @Json(name = "oldPrice") var oldPrice: Double? = null,
    @Json(name = "isNew") var isNew: Boolean? = null,
    @Json(name = "isExclusive") var isExclusive: Boolean? = null,
)


data class ItemDetail(
    @Json(name = "id") var id: Int? = null,
    @Json(name = "colorId") var colorId: Int? = null,
    @Json(name = "colorTitle") var colorTitle: String? = null,
    @Json(name = "sizeId") var sizeId: Int? = null,
    @Json(name = "sizeTitle") var sizeTitle: String? = null,
    @Json(name = "price") var price: Double? = null,
    @Json(name = "discountPercentage") var discountPercentage: String? = null,
    @Json(name = "oldPrice") var oldPrice: Double? = null,
    @Json(name = "sku") var sku: String? = null,
    @Json(name = "itemBalance") var itemBalance: Int? = null,
    @Json(name = "quantity") var quantity: Int? = null
)

data class ItemImages(
    @Json(name = "colorId") var colorId: Int? = null,
    @Json(name = "imagePath") var imagePath: String? = null
)

data class RelatedItems(
    @Json(name = "id") var id: Int? = null,
    @Json(name = "brandId") var brandId: Int? = null,
    @Json(name = "categoryId") var categoryId: Int? = null,
    @Json(name = "categoryTitle") var categoryTitle: String? = null,
    @Json(name = "categoryImagePath") var categoryImagePath: String? = null,
    @Json(name = "brandTitle") var brandTitle: String? = null,
    @Json(name = "brandImagePath") var brandImagePath: String? = null,
    @Json(name = "title") var title: String? = null,
    @Json(name = "displayOrder") var displayOrder: Int? = null,
    @Json(name = "imagePath") var imagePath: String? = null,
    @Json(name = "price") var price: Double? = null,
    @Json(name = "discountPercentage") var discountPercentage: Double? = null,
    @Json(name = "oldPrice") var oldPrice: Double? = null,
    @Json(name = "isNew") var isNew: Boolean? = null,
    @Json(name = "isExclusive") var isExclusive: Boolean? = null
)