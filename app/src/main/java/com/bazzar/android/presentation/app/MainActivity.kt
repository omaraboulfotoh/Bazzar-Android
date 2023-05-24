package com.bazzar.android.presentation.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.android.local.Constants
import com.android.local.SharedPrefersManager
import com.bazzar.android.presentation.DeepLinkConstants.BAZZAR_DETAILS_DEEP_LINK
import com.bazzar.android.presentation.DeepLinkConstants.BRAND_ID
import com.bazzar.android.presentation.DeepLinkConstants.BRAND_PRODUCT_LIST_DEEP_LINK
import com.bazzar.android.presentation.DeepLinkConstants.CATEGORY_ID
import com.bazzar.android.presentation.DeepLinkConstants.CATEGORY_PRODUCT_LIST_DEEP_LINK
import com.bazzar.android.presentation.DeepLinkConstants.ITEM_ID
import com.bazzar.android.presentation.DeepLinkConstants.MARKETER_ID
import com.bazzar.android.presentation.DeepLinkConstants.PRODUCT_DETAILS_DEEP_LINK
import com.bazzar.android.presentation.composables.permissionRequester.NotificationPermissionRequester
import com.bazzar.android.presentation.theme.BazzarComposeTheme
import com.bazzar.android.utils.ContextWrapper
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var globalState: IGlobalState

    private var isDeeplinkShown = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleNotificationClickWhenAppIsNotRunning()
        setContent {
            BazzarComposeTheme {
                App(globalState)
                NotificationPermissionRequester(
                    onPermissionGranted = { },
                    onPermissionDenied = { },
                )
            }
        }
    }

    /**
     * Handle notification click when click on notification and the app is closed.
     *
     * if the app is in background and received a notification when click on that notification
     * the deeplink is not work cause [FirebaseMessagingService.onMessageReceived] is not called
     * so the only is check and retrieve the data through the intent.
     * For more info https://firebase.google.com/support/faq/#fcm-android-background
     */
    private fun handleNotificationClickWhenAppIsNotRunning() {
        val itemId = intent?.getStringExtra(ITEM_ID)
        val brandId = intent?.getStringExtra(BRAND_ID)
        val categoryId = intent?.getStringExtra(CATEGORY_ID)
        val marketerId = intent?.getStringExtra(MARKETER_ID)

        val deepLinkTarget = when {
            itemId.isNullOrEmpty().not() -> "${PRODUCT_DETAILS_DEEP_LINK}/$itemId"
            brandId.isNullOrEmpty().not() -> "${BRAND_PRODUCT_LIST_DEEP_LINK}/$brandId"
            categoryId.isNullOrEmpty().not() -> "${CATEGORY_PRODUCT_LIST_DEEP_LINK}/$categoryId"
            marketerId.isNullOrEmpty().not() -> "${BAZZAR_DETAILS_DEEP_LINK}/$marketerId"
            else -> ""
        }

        if (deepLinkTarget.isNotEmpty()) {
            Intent(
                Intent.ACTION_VIEW,
                deepLinkTarget.toUri()
            ).apply {
                startActivity(this)
                finish()
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        newBase?.let {
            val sharedPrefers = it.getSharedPreferences(Constants.sharedPreferencesName, 0)
            val prefersManager = SharedPrefersManager(sharedPrefers)
            val locale = Locale(prefersManager.getAppLanguage())
            val context = ContextWrapper.wrap(it, locale)
            super.attachBaseContext(context)
        }
    }

    companion object {
        fun restartApp(activity: AppCompatActivity) {
            activity.finish()
            activity.startActivity(Intent(activity, MainActivity::class.java))
        }
    }
}
