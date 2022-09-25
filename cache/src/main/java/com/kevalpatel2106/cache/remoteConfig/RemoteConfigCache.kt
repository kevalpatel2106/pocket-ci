package com.kevalpatel2106.cache.remoteConfig

interface RemoteConfigCache {
    fun getString(key: String): String
    fun getLong(key: String): Long
}
