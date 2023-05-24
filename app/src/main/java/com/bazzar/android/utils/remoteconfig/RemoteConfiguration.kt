package com.bazzar.android.utils.remoteconfig

interface RemoteConfiguration {
    fun init(minimumFetchIntervalSeconds: Long = DEFAULT_MINIMUM_FETCH_INTERVAL_IN_SECONDS)

    fun fetchConfigFromRemote()

    fun getString(key: String): String
    fun getBoolean(key: String): Boolean
    fun getDouble(key: String): Double
    fun getLong(key: String): Long
    fun getInt(key: String): Int

    companion object {
        private const val DEFAULT_MINIMUM_FETCH_INTERVAL_IN_SECONDS = 0L
    }
}
