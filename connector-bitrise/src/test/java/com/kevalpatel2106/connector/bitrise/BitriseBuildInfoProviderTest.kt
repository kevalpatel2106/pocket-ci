package com.kevalpatel2106.connector.bitrise

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.bitrise.network.BitriseRetrofitClient
import com.kevalpatel2106.connector.bitrise.network.dto.BuildDto
import com.kevalpatel2106.connector.bitrise.network.dto.BuildLogsDto
import com.kevalpatel2106.connector.bitrise.network.dto.PagingDto
import com.kevalpatel2106.connector.bitrise.network.dto.ResponseDto
import com.kevalpatel2106.connector.bitrise.network.endpoint.BitriseEndpoint
import com.kevalpatel2106.connector.bitrise.network.mapper.BuildMapper
import com.kevalpatel2106.coreTest.getAccountBasicFixture
import com.kevalpatel2106.coreTest.getBuildFixture
import com.kevalpatel2106.coreTest.getBuildIdFixture
import com.kevalpatel2106.coreTest.getProjectBasicFixture
import com.kevalpatel2106.coreTest.getUrlFixture
import com.kevalpatel2106.entity.PagedData
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class BitriseBuildInfoProviderTest {
    private val fixture = KFixture()
    private val build = getBuildFixture(fixture)
    private val accountBasic = getAccountBasicFixture(fixture)
    private val projectBasic = getProjectBasicFixture(fixture)
    private val bitriseEndpoint = mock<BitriseEndpoint>()
    private val retrofitClient = mock<BitriseRetrofitClient> {
        on { getBitriseService(any(), any()) } doReturn bitriseEndpoint
    }
    private val buildMapper = mock<BuildMapper> {
        on { invoke(any(), any()) } doReturn build
    }

    private val subject = BitriseBuildInfoProvider(retrofitClient, buildMapper)

    @Test
    fun `given cursor when builds page api call made then verify api call parameters`() = runTest {
        val limit = fixture<Int>()
        val cursor = fixture<String>()
        val response = ResponseDto(
            data = listOf<BuildDto>(fixture(), fixture()),
            paging = fixture<PagingDto>(),
            message = fixture(),
        )
        whenever(bitriseEndpoint.getBuildsByCreatedAt(any(), any(), any(), any()))
            .thenReturn(response)

        subject.getBuildsByCreatedDesc(projectBasic, accountBasic, cursor, limit)

        verify(bitriseEndpoint).getBuildsByCreatedAt(
            appSlug = projectBasic.remoteId.getValue(),
            sortBy = "created_at",
            next = cursor,
            limit = limit,
        )
    }

    @Test
    fun `given builds page api success when build list loaded then verify paged date`() = runTest {
        val response = ResponseDto(
            data = listOf<BuildDto>(fixture(), fixture()),
            paging = fixture<PagingDto>(),
            message = fixture(),
        )
        whenever(bitriseEndpoint.getBuildsByCreatedAt(any(), any(), any(), any()))
            .thenReturn(response)

        val pagedData = subject.getBuildsByCreatedDesc(
            projectBasic,
            accountBasic,
            fixture(),
            fixture(),
        )

        val expected = PagedData(
            data = listOf(build, build),
            nextCursor = response.paging?.nextCursor.orEmpty(),
        )
        assertEquals(expected, pagedData)
    }

    @Test
    fun `given build logs not archived when get build logs called then verify result is empty`() =
        runTest {
            whenever(bitriseEndpoint.getBuildsLogs(any(), any()))
                .thenReturn(BuildLogsDto(isArchived = false, getUrlFixture(fixture).value))

            val actual =
                subject.getBuildLogs(projectBasic, accountBasic, getBuildIdFixture(fixture))

            assertEquals("", actual)
        }

    @Test
    fun `given logs download url available on CI when get build logs called then raw logs download called`() =
        runTest {
            val logsDownloadUrl = getUrlFixture(fixture).value
            whenever(bitriseEndpoint.getBuildsLogs(any(), any()))
                .thenReturn(BuildLogsDto(isArchived = true, logsDownloadUrl))
            val expectedLogs = fixture<String>()
            whenever(bitriseEndpoint.getRawBuildsLogs(any())).thenReturn(expectedLogs)

            subject.getBuildLogs(projectBasic, accountBasic, getBuildIdFixture(fixture))

            verify(bitriseEndpoint).getRawBuildsLogs(logsDownloadUrl)
        }

    @Test
    fun `given logs download url available on CI when get build logs called then verify logs downloaded`() =
        runTest {
            val logsDownloadUrl = getUrlFixture(fixture).value
            val expectedLogs = fixture<String>()
            whenever(bitriseEndpoint.getBuildsLogs(any(), any()))
                .thenReturn(BuildLogsDto(isArchived = true, logsDownloadUrl))
            whenever(bitriseEndpoint.getRawBuildsLogs(any())).thenReturn(expectedLogs)

            val actual = subject.getBuildLogs(
                projectBasic,
                accountBasic,
                getBuildIdFixture(fixture),
            )

            assertEquals(expectedLogs, actual)
        }
}
