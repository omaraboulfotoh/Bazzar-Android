package com.android.model.home

fun RelatedItems.toProduct() =
    Product(
        id = this.id,
        imagePath = this.imagePath,
        title = this.title,
        brandTitle = this.brandTitle,
        oldPrice = this.oldPrice,
        price = this.price,
        brandId = this.brandId,
        categoryId = this.categoryId,
        categoryTitle = this.categoryTitle,
        displayOrder = this.displayOrder,
        isExclusive = this.isExclusive,
        isNew = this.isNew ?: false,
        discountPercentage = this.discountPercentage
    )