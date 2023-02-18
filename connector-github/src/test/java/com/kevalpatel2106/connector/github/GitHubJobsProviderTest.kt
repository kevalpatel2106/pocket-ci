package com.kevalpatel2106.connector.github

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.github.network.GitHubRetrofitClient
import com.kevalpatel2106.connector.github.network.dto.JobDto
import com.kevalpatel2106.connector.github.network.dto.JobListDto
import com.kevalpatel2106.connector.github.network.endpoint.GitHubEndpoint
import com.kevalpatel2106.connector.github.network.mapper.JobMapper
import com.kevalpatel2106.coreTest.buildHttpException
import com.kevalpatel2106.coreTest.getAccountBasicFixture
import com.kevalpatel2106.coreTest.getBuildIdFixture
import com.kevalpatel2106.coreTest.getJobFixture
import com.kevalpatel2106.coreTest.getProjectBasicFixture
import com.kevalpatel2106.entity.Job
import com.kevalpatel2106.entity.PagedData
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import java.net.HttpURLConnection.HTTP_GONE
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR

internal class GitHubJobsProviderTest {
    private val mappedJob = getJobFixture(fixture)
    private val projectBasic = getProjectBasicFixture(fixture)
    private val accountBasic = getAccountBasicFixture(fixture)
    private val buildId = getBuildIdFixture(fixture)

    private val githubEndpoint = mock<GitHubEndpoint>()
    private val retrofitClient = mock<GitHubRetrofitClient> {
        on { getGithubService(any(), any()) } doReturn githubEndpoint
    }
    private val jobMapper = mock<JobMapper> {
        on { invoke(any(), any()) } doReturn mappedJob
    }

    private val subject = GitHubJobsProvider(retrofitClient, jobMapper)

    // region getJobs
    @ParameterizedTest(
        name = "given cursor {0} when jobs page api call made then verify api call parameters with page number {1}"
    )
    @MethodSource("provideValuesForApiParameterTest")
    fun `given cursor when jobs page api call made then verify api call parameters`(
        inputCursor: String?,
        expectedPageNumber: Int,
    ) = runTest {
        val limit = fixture<Int>()
        whenever(githubEndpoint.getJobs(any(), any(), any(), any(), any()))
            .thenReturn(fixture())

        subject.getJobs(accountBasic, projectBasic, buildId, inputCursor, limit)

        verify(githubEndpoint).getJobs(
            projectBasic.owner,
            projectBasic.name,
            buildId.getValue(),
            limit,
            expectedPageNumber,
        )
    }

    @ParameterizedTest(
        name = "given current cursor {0} and response job list when jobs page api call made then verify next page cursor {2}"
    )
    @MethodSource("provideValuesForNextCursorTest")
    fun `given current cursor and response job list when jobs page api call made then verify next page cursor`(
        currentCursor: String?,
        responseJobs: List<JobDto>,
        expectedNextCursor: String?,
    ) = runTest {
        whenever(githubEndpoint.getJobs(any(), any(), any(), any(), any()))
            .thenReturn(JobListDto(fixture(), responseJobs))

        val data =
            subject.getJobs(accountBasic, projectBasic, buildId, currentCursor, fixture())

        assertEquals(expectedNextCursor, data.nextCursor)
    }

    @Test
    fun `given non-empty response jobs list when jobs page api call made then verify paged data`() =
        runTest {
            val jobDtos = listOf<JobDto>(fixture(), fixture())
            whenever(githubEndpoint.getJobs(any(), any(), any(), any(), any()))
                .thenReturn(JobListDto(fixture(), jobDtos))

            val data = subject.getJobs(accountBasic, projectBasic, buildId, fixture(), fixture())

            assertEquals(listOf(mappedJob, mappedJob), data.data)
        }

    @Test
    fun `given empty response jobs list when jobs page api call made then verify paged data`() =
        runTest {
            whenever(githubEndpoint.getJobs(any(), any(), any(), any(), any()))
                .thenReturn(JobListDto(fixture(), listOf()))

            val data = subject.getJobs(accountBasic, projectBasic, buildId, fixture(), fixture())

            assertEquals(PagedData(listOf<Job>(), null), data)
        }

    @Test
    fun `given error getting jobs when jobs page loads then exception thrown`() = runTest {
        whenever(githubEndpoint.getJobs(any(), any(), any(), any(), any()))
            .thenThrow(IllegalStateException())

        assertThrows<IllegalStateException> {
            subject.getJobs(accountBasic, projectBasic, buildId, fixture(), fixture())
        }
    }
    // endregion

    // region getJobLogs
    @Test
    fun `given job logs can be downloaded from CI when get job logs called then verify result`() =
        runTest {
            val testLogs = fixture<String>()
            whenever(githubEndpoint.getJobLogs(any(), any(), any())).thenReturn(testLogs)

            val actual = subject.getJobLogs(accountBasic, projectBasic, buildId, fixture())

            assertEquals(testLogs, actual)
        }

    @Test
    fun `given 410 code returned from CI when get job logs called then verify empty logs returned`() =
        runTest {
            whenever(githubEndpoint.getJobLogs(any(), any(), any()))
                .thenThrow(buildHttpException(HTTP_GONE, fixture))

            val actual = subject.getJobLogs(accountBasic, projectBasic, buildId, fixture())

            assertEquals("", actual)
        }

    @Test
    fun `given 500 code returned from CI when get job logs called then verify error thrown`() =
        runTest {
            whenever(githubEndpoint.getJobLogs(any(), any(), any()))
                .thenThrow(buildHttpException(HTTP_INTERNAL_ERROR, fixture))

            val error = assertThrows<HttpException> {
                subject.getJobLogs(accountBasic, projectBasic, buildId, fixture())
            }
            assertEquals(HTTP_INTERNAL_ERROR, error.code())
        }

    @Test
    fun `given generic exception occurred when get job logs called then verify error thrown`() =
        runTest {
            whenever(githubEndpoint.getJobLogs(any(), any(), any()))
                .thenThrow(IllegalStateException())

            assertThrows<IllegalStateException> {
                subject.getJobLogs(accountBasic, projectBasic, buildId, fixture())
            }
        }
    // endregion

    companion object {
        private val fixture = KFixture()

        @Suppress("unused")
        @JvmStatic
        fun provideValuesForApiParameterTest() = listOf(
            // Format current cursor, cursor in api parameters
            arguments("1", 1),
            arguments("invalid-cursor", 1),
            arguments(null, 1),
        )

        @Suppress("unused")
        @JvmStatic
        fun provideValuesForNextCursorTest() = listOf(
            // Format current page, response job list, next cursor
            arguments(null, listOf<JobDto>(), null),
            arguments(null, listOf<JobDto>(fixture(), fixture()), "2"),

            arguments("invalid-cursor", listOf<JobDto>(), null),
            arguments("invalid-cursor", listOf<JobDto>(fixture(), fixture()), "2"),

            arguments("100", listOf<JobDto>(), null),
            arguments("100", listOf<JobDto>(fixture(), fixture()), "101"),
        )
    }
}
