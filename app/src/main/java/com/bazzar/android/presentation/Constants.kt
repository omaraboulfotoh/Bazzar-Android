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
        LatLng(28.5244, 47.3951),
        LatLng(30.0971, 48.1292),
    )

    val KUWAIT_CITY_LAT_LAN = LatLng(29.375042, 47.978197)
}

object DeepLinkConstants {
    const val ITEM_ID = "ItemId"
    const val BRAND_ID = "BrandId"
    const val CATEGORY_ID = "CategoryId"
    const val PRODUCT_DETAILS_DEEP_LINK = "bazzar://product-details"
    const val BRAND_PRODUCT_LIST_DEEP_LINK = "bazzar://brand-product-list"
    const val CATEGORY_PRODUCT_LIST_DEEP_LINK = "bazzar://category-product-list"
}