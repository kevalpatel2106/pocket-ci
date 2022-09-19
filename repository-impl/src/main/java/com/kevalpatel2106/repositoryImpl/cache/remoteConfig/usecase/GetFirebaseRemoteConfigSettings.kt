package com.kevalpatel2106.repositoryImpl.cache.remoteConfig.usecase

import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

internal interface GetFirebaseRemoteConfigSettings {
    operator fun invoke(): FirebaseRemoteConfigSettings
}
