package com.kevalpatel2106.connector.github

import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.Url
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class GithubInfoProviderTest {
    private val subject = GithubInfoProvider()

    @Test
    fun `check ci type is github`() = runTest {
        assertEquals(CIType.GITHUB, subject.getCIInfo().type)
    }

    @Test
    fun `check ci name is github actions`() = runTest {
        assertEquals(R.string.github_actions, subject.getCIInfo().name)
    }

    @Test
    fun `check default base url is github api ending with back slash`() = runTest {
        assertEquals(Url("https://api.github.com/"), subject.getCIInfo().defaultBaseUrl)
    }

    @Test
    fun `check icon is github logo`() = runTest {
        assertEquals(R.drawable.logo_github, subject.getCIInfo().icon)
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
    fun `check supports jobs`() = runTest {
        assertTrue(subject.getCIInfo().supportJobs)
    }

    @Test
    fun `check supports job level logs`() = runTest {
        assertTrue(subject.getCIInfo().supportJobLevelLogs)
    }

    @Test
    fun `check does not support custom base url`() = runTest {
        assertFalse(subject.getCIInfo().supportCustomBaseUrl)
    }

    @Test
    fun `check does not support build level logs`() = runTest {
        assertFalse(subject.getCIInfo().supportBuildLevelLogs)
    }
}
