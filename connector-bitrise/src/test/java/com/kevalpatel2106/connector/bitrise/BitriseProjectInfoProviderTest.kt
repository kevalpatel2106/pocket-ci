package com.kevalpatel2106.connector.bitrise

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.bitrise.network.BitriseRetrofitClient
import com.kevalpatel2106.connector.bitrise.network.dto.PagingDto
import com.kevalpatel2106.connector.bitrise.network.dto.ProjectDto
import com.kevalpatel2106.connector.bitrise.network.dto.ResponseDto
import com.kevalpatel2106.connector.bitrise.network.endpoint.BitriseEndpoint
import com.kevalpatel2106.connector.bitrise.usecase.ConvertProjectsWithLastUpdateTime
import com.kevalpatel2106.coreTest.getAccountBasicFixture
import com.kevalpatel2106.coreTest.getProjectFixture
import com.kevalpatel2106.entity.PagedData
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class BitriseProjectInfoProviderTest {
    private val fixture = KFixture()
    private val mappedProjects = listOf(getProjectFixture(fixture), getProjectFixture(fixture))
    private val accountBasic = getAccountBasicFixture(fixture)

    private val bitriseEndpoint = mock<BitriseEndpoint>()
    private val retrofitClient = mock<BitriseRetrofitClient> {
        on { getBitriseService(any(), any()) } doReturn bitriseEndpoint
    }
    private val convertProjectsWithLastUpdateTime = mock<ConvertProjectsWithLastUpdateTime> {
        on { invoke(any(), any(), any()) } doReturn mappedProjects
    }

    private val subject = BitriseProjectInfoProvider(
        retrofitClient,
        convertProjectsWithLastUpdateTime,
    )

    @Test
    fun `given cursor when projects page api call made then verify api call parameters`() =
        runTest {
            val limit = fixture<Int>()
            val cursor = fixture<String>()
            val response = ResponseDto(
                data = listOf<ProjectDto>(fixture(), fixture()),
                paging = fixture<PagingDto>(),
                message = fixture(),
            )
            whenever(bitriseEndpoint.getProjectsLastBuildAt(any(), any(), any()))
                .thenReturn(response)

            subject.getProjectsUpdatedDesc(accountBasic, cursor, limit)

            verify(bitriseEndpoint).getProjectsLastBuildAt(
                sortBy = "last_build_at",
                next = cursor,
                limit = limit,
            )
        }

    @Test
    fun `given projects page api success when project list loaded then verify paged date`() =
        runTest {
            val response = ResponseDto(
                data = listOf<ProjectDto>(fixture(), fixture()),
                paging = fixture<PagingDto>(),
                message = fixture(),
            )
            whenever(bitriseEndpoint.getProjectsLastBuildAt(any(), any(), any()))
                .thenReturn(response)

            val pagedData = subject.getProjectsUpdatedDesc(accountBasic, fixture(), fixture())

            val expected = PagedData(
                data = mappedProjects,
                nextCursor = response.paging?.nextCursor.orEmpty(),
            )
            assertEquals(expected, pagedData)
        }
}
