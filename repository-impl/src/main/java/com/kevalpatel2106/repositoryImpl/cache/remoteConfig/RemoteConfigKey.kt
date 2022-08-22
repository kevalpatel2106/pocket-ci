package com.kevalpatel2106.repositoryImpl.cache.remoteConfig

internal enum class RemoteConfigKey(val value: String) {
    // NOTE: Value should match with the key entered in firebase console.
    PRIVACY_POLICY("PrivacyPolicy"),
    CHANGELOG("Changelog"),
}
