package com.kevalpatel2106.repositoryImpl.cache.remoteConfig

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.kevalpatel2106.repositoryImpl.di.IsDebug
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class FirebaseRemoteConfigCache @Inject constructor(
    private val firebaseRemoteConfig: FirebaseRemoteConfig,
) : RemoteConfigCache {

    init {
        fetch()
    }

    private fun fetch() {
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.i("Firebase config params updated.")
                } else {
                    Timber.i("Firebase config params fetch failed.")
                }
            }.addOnFailureListener { error ->
                Timber.e(error)
            }
    }

    override fun getString(key: String) = firebaseRemoteConfig.getString(key)

    override fun getLong(key: String) = firebaseRemoteConfig.getLong(key)

    class Factory @Inject constructor(@IsDebug private val isDebug: Boolean) {

        fun create(): FirebaseRemoteConfig {
            val fetchInterval = if (isDebug) FETCH_INTERVAL_IN_DEBUG else FETCH_INTERVAL_IN_PROD
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(fetchInterval).build()
            return FirebaseRemoteConfig.getInstance()
                .apply { setConfigSettingsAsync(configSettings) }
        }

        companion object {
            private val FETCH_INTERVAL_IN_PROD = TimeUnit.MINUTES.toSeconds(30)
            private val FETCH_INTERVAL_IN_DEBUG = TimeUnit.MINUTES.toSeconds(15)
        }
    }
}
