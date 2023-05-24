package com.bazzar.android.utils.remoteconfig

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

@Singleton
class FirebaseRemoteConfiguration @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseRemoteConfig: FirebaseRemoteConfig,
) : RemoteConfiguration {

    override fun init(minimumFetchIntervalSeconds: Long) {
        setConfigSettings(minimumFetchIntervalSeconds)
        readDefaults()
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

    private fun setDefaultsAsync(defaultsMap: Map<String, Any>) {
        firebaseRemoteConfig.setDefaultsAsync(defaultsMap)
            .logResult("Done setting the defaults!")
    }

    @SuppressLint("CheckResult")
    private fun readDefaults() {
        val fileContent =
            context.assets.open(REMOTE_CONFIG_FILE_NAME).bufferedReader().use { it.readText() }
        try {
            val jsonElement = Json.parseToJsonElement(fileContent).jsonObject
            setDefaultsAsync(jsonElement)
        } catch (throwable: Throwable) {
            // ignore
        }
    }

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
