package com.bazzar.android.presentation

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

object Constants {
    const val MAIN_CATEGORY_LIST_KEY = "main_category_list_key"
    const val SUB_CATEGORY_ID_KEY = "sub_category_id_key"
    const val BRAND_KEY = "brand_key"
    const val PRODUCT_KEY = "product_key"
}

object SocialMedia {
    const val FACEBOOK_PAGE = "https://www.facebook.com/bazzar.gate1"
    const val INSTAGRAM_PAGE = "https://www.instagram.com/bazzar.gate/?igshid=YmMyMTA2M2Y%3D"
    const val TWITTER_PAGE = "https://twitter.com/bazzargate?s=11&t=Sx1hvXm0_rH6joDkDWScRw"
    const val WHATS_APP = "https://api.whatsapp.com/send?phone=$+96594124128"
}

object MapLatLngConstants {
    val KUWAIT_LAT_LNG_BOUNDS = LatLngBounds(
        LatLng(28.5364, 46.5552),
        LatLng(30.1073, 48.4371),
    )

    val KUWAIT_CITY_LAT_LAN = LatLng(29.375042, 47.978197)
}

object DeepLinkConstants {
    const val ITEM_ID = "ItemId"
    const val BRAND_ID = "BrandId"
    const val CATEGORY_ID = "CategoryId"
    const val CUSTOM_IMAGE = "image"
    const val MARKETER_ID = "MarketerId"

    const val PRODUCT_DETAILS_DEEP_LINK = "bazzar://product-details"
    const val PRODUCT_DETAILS_HTTP_DEEP_LINK = "https://admin.bzaaarz.com/share/product"

    const val BRAND_PRODUCT_LIST_DEEP_LINK = "bazzar://brand-product-list"
    const val CATEGORY_PRODUCT_LIST_DEEP_LINK = "bazzar://category-product-list"
    const val BRAND_PRODUCT_LIST_HTTP_DEEP_LINK = "https://admin.bzaaarz.com/Share/Brand"

    const val BAZZAR_DETAILS_DEEP_LINK = "bazzar://bazzar-details"
    const val BAZZAR_DETAILS_HTTP_DEEP_LINK = "https://admin.bzaaarz.com/Share/Marketer"
}