package com.kevalpatel2106.repository

interface AppConfigRepo {

    fun isDebugBuild(): Boolean

    fun getVersionName(): String

    fun getVersionCode(): Int
}
