package com.kevalpatel2106.connector.bitrise

import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.Url
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class BitriseInfoProviderTest {
    private val subject = BitriseInfoProvider()

    @Test
    fun `check ci type is github`() = runTest {
        assertEquals(CIType.BITRISE, subject.getCIInfo().type)
    }

    @Test
    fun `check ci name is github actions`() = runTest {
        assertEquals(R.string.bitrise, subject.getCIInfo().name)
    }

    @Test
    fun `check default base url is github api ending with back slash`() = runTest {
        assertEquals(Url("https://api.bitrise.io/"), subject.getCIInfo().defaultBaseUrl)
    }

    @Test
    fun `check icon is github logo`() = runTest {
        assertEquals(R.drawable.logo_bitrise, subject.getCIInfo().icon)
    }

    @Test
    fun `check supports viewing artifacts`() = runTest {
        assertTrue(subject.getCIInfo().supportViewArtifacts)
    }

    @Test
    fun `check supports downloading artifacts`() = runTest {
        assertTrue(subject.getCIInfo().supportDownloadArtifacts)
    }

    @Test
    fun `check does not supports jobs`() = runTest {
        assertFalse(subject.getCIInfo().supportJobs)
    }

    @Test
    fun `check does not supports job level logs`() = runTest {
        assertFalse(subject.getCIInfo().supportJobLevelLogs)
    }

    @Test
    fun `check does not support custom base url`() = runTest {
        assertFalse(subject.getCIInfo().supportCustomBaseUrl)
    }

    @Test
    fun `check support build level logs`() = runTest {
        assertTrue(subject.getCIInfo().supportBuildLevelLogs)
    }
}
