package com.kevalpatel2106.repositoryImpl.cache.remoteConfig.usecase

import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.kevalpatel2106.repository.di.IsDebug
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class GetFirebaseRemoteConfigSettingsImpl @Inject constructor(
    @IsDebug private val isDebug: Boolean,
) : GetFirebaseRemoteConfigSettings {

    override fun invoke(): FirebaseRemoteConfigSettings {
        val fetchInterval = if (isDebug) FETCH_INTERVAL_IN_DEBUG else FETCH_INTERVAL_IN_PROD
        return FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(fetchInterval)
            .build()
    }

    companion object {
        private val FETCH_INTERVAL_IN_PROD = TimeUnit.MINUTES.toSeconds(30)
        private val FETCH_INTERVAL_IN_DEBUG = TimeUnit.MINUTES.toSeconds(15)
    }
}
