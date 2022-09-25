package com.kevalpatel2106.repositoryImpl.remoteConfig

import com.kevalpatel2106.entity.toUrl
import com.kevalpatel2106.repository.RemoteConfigRepo
import com.kevalpatel2106.cache.remoteConfig.RemoteConfigCache
import com.kevalpatel2106.cache.remoteConfig.RemoteConfigKey
import com.kevalpatel2106.cache.remoteConfig.RemoteConfigKey.CHANGELOG
import com.kevalpatel2106.cache.remoteConfig.RemoteConfigKey.PRIVACY_POLICY
import javax.inject.Inject

internal class RemoteConfigRepoImpl @Inject constructor(
    private val remoteConfigCache: RemoteConfigCache,
) : RemoteConfigRepo {

    override fun getPrivacyPolicy() = getUrl(PRIVACY_POLICY)

    override fun getChangelog() = getUrl(CHANGELOG)

    private fun getUrl(key: RemoteConfigKey) = remoteConfigCache.getString(key.value).toUrl()
}
