package com.kevalpatel2106.cache.remoteConfig.usecase

import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

internal interface GetFirebaseRemoteConfigSettings {
    operator fun invoke(): FirebaseRemoteConfigSettings
}
