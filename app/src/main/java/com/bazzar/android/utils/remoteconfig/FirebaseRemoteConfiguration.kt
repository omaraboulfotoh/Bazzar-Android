package com.bazzar.android.utils.remoteconfig

import android.content.Context
import android.util.Log
import com.bazzar.android.R
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRemoteConfiguration @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseRemoteConfig: FirebaseRemoteConfig,
) : RemoteConfiguration {

    override fun init(minimumFetchIntervalSeconds: Long) {
        setConfigSettings(minimumFetchIntervalSeconds)
        fetchConfigFromRemote()
    }

    private fun setConfigSettings(minimumFetchIntervalSeconds: Long) {
        firebaseRemoteConfig.setConfigSettingsAsync(
            FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(minimumFetchIntervalSeconds)
                .build()
        ).logResult("Done setting configuration settings!")
    }

    override fun fetchConfigFromRemote() {
        firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        firebaseRemoteConfig.fetchAndActivate()
        firebaseRemoteConfig.fetchAndActivate()
        firebaseRemoteConfig.activate()
        firebaseRemoteConfig.fetch()
        firebaseRemoteConfig.activate()
    }

    override fun getString(key: String) =
        firebaseRemoteConfig.getString(key)

    override fun getBoolean(key: String) =
        firebaseRemoteConfig.getBoolean(key)

    override fun getDouble(key: String) =
        firebaseRemoteConfig.getDouble(key)

    override fun getLong(key: String) =
        firebaseRemoteConfig.getLong(key)

    override fun getInt(key: String) =
        firebaseRemoteConfig.getLong(key).toInt()

    private fun <TResult> Task<TResult>.logResult(successMessage: String) {
        this.addOnSuccessListener {
            Log.i("FirebaseRemoteConfig", successMessage)
        }.addOnFailureListener {
            it.printStackTrace()
        }
    }

    companion object {
        private const val REMOTE_CONFIG_FILE_NAME = "configuration_defaults.json"
    }
}
