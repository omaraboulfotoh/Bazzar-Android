package com.bazzar.android.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.bazzar.android.R
import com.bazzar.android.presentation.DeepLinkConstants.BAZZAR_DETAILS_DEEP_LINK
import com.bazzar.android.presentation.DeepLinkConstants.BRAND_ID
import com.bazzar.android.presentation.DeepLinkConstants.BRAND_PRODUCT_LIST_DEEP_LINK
import com.bazzar.android.presentation.DeepLinkConstants.CATEGORY_ID
import com.bazzar.android.presentation.DeepLinkConstants.CATEGORY_PRODUCT_LIST_DEEP_LINK
import com.bazzar.android.presentation.DeepLinkConstants.ITEM_ID
import com.bazzar.android.presentation.DeepLinkConstants.MARKETER_ID
import com.bazzar.android.presentation.DeepLinkConstants.PRODUCT_DETAILS_DEEP_LINK
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber

class MessagingService : FirebaseMessagingService() {

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Timber.d("pushNotification: $remoteMessage")
        Timber.d("From: ${remoteMessage.from}")
        if (remoteMessage.notification != null) {
            sendNotification(remoteMessage)
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Timber.d("Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        sendRegistrationToServer(token)

        scheduleJob()
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private fun sendRegistrationToServer(token: String) {
        Timber.d("sendRegistrationTokenToServer($token)")
        // storage.fcmToken = token
    }

    /**
     * Schedule async work using WorkManager.
     */
    private fun scheduleJob() {
        // [START dispatch_job]
        val work = OneTimeWorkRequest
            .Builder(FCMWorker::class.java)
            .build()
        WorkManager.getInstance(this).beginWith(work).enqueue()
        // [END dispatch_job]
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param remoteMessage FCM message body received.
     */
    private fun sendNotification(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification?.title
        val messageBody = remoteMessage.notification?.body
        // val imageUrl = remoteMessage.notification?.imageUrl
        val itemId = remoteMessage.data[ITEM_ID]
        val brandId = remoteMessage.data[BRAND_ID]
        val categoryId = remoteMessage.data[CATEGORY_ID]
        val marketerId = remoteMessage.data[MARKETER_ID]

        val deepLinkTarget = when {
            itemId.isNullOrEmpty().not() -> "${PRODUCT_DETAILS_DEEP_LINK}/$itemId"
            brandId.isNullOrEmpty().not() -> "${BRAND_PRODUCT_LIST_DEEP_LINK}/$brandId"
            categoryId.isNullOrEmpty().not() -> "${CATEGORY_PRODUCT_LIST_DEEP_LINK}/$categoryId"
            marketerId.isNullOrEmpty().not() -> "${BAZZAR_DETAILS_DEEP_LINK}/$marketerId"
            else -> ""
        }

        val intent = Intent(Intent.ACTION_VIEW, deepLinkTarget.toUri())
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_bazzar_circular)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}