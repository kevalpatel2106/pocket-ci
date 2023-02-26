package com.kevalpatel2106.cache.remoteConfig.usecase

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

internal class GetFirebaseRemoteConfigSettingsImplTest {

    @Test
    fun `given debug build when getting settings then check minimum fetch interval is 15 minutes`() {
        val actual = getSubject(isDebug = true)().minimumFetchIntervalInSeconds

        assertEquals(TimeUnit.MINUTES.toSeconds(15), actual)
    }

    @Test
    fun `given debug build when getting settings then check minimum fetch interval is 30 minutes`() {
        val actual = getSubject(isDebug = false)().minimumFetchIntervalInSeconds

        assertEquals(TimeUnit.MINUTES.toSeconds(30), actual)
    }

    private fun getSubject(isDebug: Boolean) = GetFirebaseRemoteConfigSettingsImpl(isDebug)
}
