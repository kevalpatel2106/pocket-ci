package com.kevalpatel2106.repositoryImpl.cache.remoteConfig

internal interface RemoteConfigCache {
    fun getString(key: String): String
    fun getLong(key: String): Long
}
