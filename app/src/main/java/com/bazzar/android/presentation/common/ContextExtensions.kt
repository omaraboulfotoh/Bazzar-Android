package com.bazzar.android.presentation.common

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.bazzar.android.R

fun Context.openPhoneIntent(phoneNumber: String) = try {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    startActivity(intent)
} catch (t: Throwable) {
    Toast.makeText(
        this,
        R.string.no_phone_apps_found_on_your_device,
        Toast.LENGTH_LONG
    ).show()
}

fun Context.openEmailIntent(
    emailAddress: String,
    subject: String = "",
    body: String = "",
) = try {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }
    startActivity(Intent.createChooser(intent, ""))
} catch (t: Throwable) {
    Toast.makeText(
        this,
        R.string.no_email_apps_found_on_your_device,
        Toast.LENGTH_LONG
    ).show()
}

fun Context.shareText(text: String, shareLink: String? = null) {
    if (shareLink.isNullOrEmpty().not()) {
        createShareIntent("$text\n$shareLink")
    } else {
        createShareIntent(text)
    }
}

private fun Context.createShareIntent(shareableText: String) {
    try {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareableText)
        }
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(
            this,
            R.string.can_not_find_a_suitable_app_to_preform_that_action,
            Toast.LENGTH_SHORT
        ).show()
    }
}
