package com.kevalpatel2106.repositoryImpl.remoteConfig

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.cache.remoteConfig.RemoteConfigCache
import com.kevalpatel2106.cache.remoteConfig.RemoteConfigKey
import com.kevalpatel2106.coreTest.getUrlFixture
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.net.MalformedURLException

internal class RemoteConfigRepoImplTest {
    private val fixture = KFixture()
    private val fakeUrl = getUrlFixture(fixture)
    private val remoteConfigCache = mock<RemoteConfigCache> {
        on { getString(any()) } doReturn fakeUrl.value
    }
    private val subject = RemoteConfigRepoImpl(remoteConfigCache)

    // region getPrivacyPolicy
    @Test
    fun `given privacy policy url in cache when get privacy policy then check url fetched from cache`() {
        subject.getPrivacyPolicy()

        verify(remoteConfigCache).getString(RemoteConfigKey.PRIVACY_POLICY.value)
    }

    @Test
    fun `given cached privacy policy url is wrong format when get privacy policy then error thrown`() {
        whenever(remoteConfigCache.getString(any())).thenReturn("wrong_url")

        assertThrows<MalformedURLException> {
            subject.getPrivacyPolicy()
        }
    }
    // endregion

    // region getChangelog
    @Test
    fun `given change url in cache when get changelog then check url fetched from cache`() {
        subject.getChangelog()

        verify(remoteConfigCache).getString(RemoteConfigKey.CHANGELOG.value)
    }

    @Test
    fun `given cached changelog url is wrong format when get changelog then error thrown`() {
        whenever(remoteConfigCache.getString(any())).thenReturn("wrong_url")

        assertThrows<MalformedURLException> {
            subject.getChangelog()
        }
    }
    // endregion
}
