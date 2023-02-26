package com.kevalpatel2106.cache.remoteConfig

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import timber.log.Timber
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
}
