package com.bazzar.android

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.android.local.SharedPrefersManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.libraries.places.api.Places
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class BazzarApplication : Application() {

    @Inject
    lateinit var prefersManager: SharedPrefersManager

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        if (Places.isInitialized().not()) {
            Places.initialize(this, BuildConfig.MAP_API_KEY)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Timber.d(task.exception, "Fetching FCM registration token failed")
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result
                Timber.d("Token: $token")
                prefersManager.saveFcmToken(token)
            }
        )
    }
}