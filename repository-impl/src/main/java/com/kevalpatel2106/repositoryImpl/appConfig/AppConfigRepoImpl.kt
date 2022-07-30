package com.kevalpatel2106.repositoryImpl.appConfig

import com.kevalpatel2106.repository.AppConfigRepo
import com.kevalpatel2106.repositoryImpl.di.AppVersion
import com.kevalpatel2106.repositoryImpl.di.AppVersionCode
import com.kevalpatel2106.repositoryImpl.di.IsDebug
import javax.inject.Inject

internal class AppConfigRepoImpl @Inject constructor(
    @AppVersion private val appVersion: String,
    @AppVersionCode private val appVersionCode: Int,
    @IsDebug private val isDebug: Boolean,
) : AppConfigRepo {

    override fun isDebugBuild() = isDebug

    override fun getVersionName() = appVersion

    override fun getVersionCode() = appVersionCode
}
