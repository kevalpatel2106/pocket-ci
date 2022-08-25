package com.kevalpatel2106.connector.bitrise

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.coreTest.getAccountBasicFixture
import com.kevalpatel2106.coreTest.getBuildIdFixture
import com.kevalpatel2106.coreTest.getProjectBasicFixture
import com.kevalpatel2106.entity.CIType
import com.kevalpatel2106.entity.exception.FeatureNotSupportedException
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class BitriseJobsProviderTest {
    private val fixture = KFixture()
    private val subject = BitriseJobsProvider()

    @Test
    fun `when getting jobs then feature not supported exception thrown`() = runTest {
        val error = assertThrows<FeatureNotSupportedException> {
            subject.getJobs(
                getAccountBasicFixture(fixture),
                getProjectBasicFixture(fixture),
                getBuildIdFixture(fixture),
                fixture(),
                fixture(),
            )
        }
        assertEquals(CIType.BITRISE, error.ciType)
    }

    @Test
    fun `when getting job logs then feature not supported exception thrown`() = runTest {
        val error = assertThrows<FeatureNotSupportedException> {
            subject.getJobLogs(
                getAccountBasicFixture(fixture),
                getProjectBasicFixture(fixture),
                getBuildIdFixture(fixture),
                fixture(),
            )
        }
        assertEquals(CIType.BITRISE, error.ciType)
    }
}
