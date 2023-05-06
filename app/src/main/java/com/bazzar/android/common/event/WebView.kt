package com.bazzar.android.common.event

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.bazzar.android.R

fun openWebPageInAppBrowser(context: Context, url: String) {
    val colorSchemeParams = CustomTabColorSchemeParams.Builder().apply {
        setToolbarColor(ContextCompat.getColor(context, R.color.prussian_blue))
        setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.prussian_blue))
    }.build()

    CustomTabsIntent.Builder()
        .setDefaultColorSchemeParams(colorSchemeParams)
        .build()
        .launchUrl(context, Uri.parse(url))
}