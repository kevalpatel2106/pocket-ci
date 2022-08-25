package com.kevalpatel2106.connector.bitrise

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.bitrise.network.BitriseRetrofitClient
import com.kevalpatel2106.connector.bitrise.network.dto.ArtifactDetailItemDto
import com.kevalpatel2106.connector.bitrise.network.dto.ArtifactListItemDto
import com.kevalpatel2106.connector.bitrise.network.dto.PagingDto
import com.kevalpatel2106.connector.bitrise.network.dto.ResponseDto
import com.kevalpatel2106.connector.bitrise.network.endpoint.BitriseEndpoint
import com.kevalpatel2106.connector.bitrise.network.mapper.ArtifactListItemMapper
import com.kevalpatel2106.coreTest.getAccountBasicFixture
import com.kevalpatel2106.coreTest.getArtifactFixture
import com.kevalpatel2106.coreTest.getArtifactIdFixture
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

internal class BitriseArtifactProviderTest {
    private val fixture = KFixture()
    private val artifact = getArtifactFixture(fixture)
    private val buildId = getBuildIdFixture(fixture)
    private val accountBasic = getAccountBasicFixture(fixture)
    private val projectBasic = getProjectBasicFixture(fixture)

    private val bitriseEndpoint = mock<BitriseEndpoint>()
    private val retrofitClient = mock<BitriseRetrofitClient> {
        on { getBitriseService(any(), any()) } doReturn bitriseEndpoint
    }
    private val artifactListItemMapper = mock<ArtifactListItemMapper> {
        on { invoke(any(), any()) } doReturn artifact
    }

    private val subject = BitriseArtifactProvider(retrofitClient, artifactListItemMapper)

    @Test
    fun `given cursor when artifacts page api call made then verify api call parameters`() =
        runTest {
            val limit = fixture<Int>()
            val cursor = fixture<String>()
            val response = ResponseDto(
                data = listOf<ArtifactListItemDto>(fixture(), fixture()),
                paging = fixture<PagingDto>(),
                message = fixture(),
            )
            whenever(bitriseEndpoint.getArtifacts(any(), any(), any(), any())).thenReturn(response)

            subject.getArtifacts(projectBasic, accountBasic, buildId, cursor, limit)

            verify(bitriseEndpoint).getArtifacts(
                appSlug = projectBasic.remoteId.getValue(),
                buildSlug = buildId.getValue(),
                next = cursor,
                limit = limit,
            )
        }

    @Test
    fun `given artifacts page api success when artifact list loaded then verify paged date`() =
        runTest {
            val response = ResponseDto(
                data = listOf<ArtifactListItemDto>(fixture(), fixture()),
                paging = fixture<PagingDto>(),
                message = fixture(),
            )
            whenever(bitriseEndpoint.getArtifacts(any(), any(), any(), any())).thenReturn(response)

            val pagedData = subject.getArtifacts(
                projectBasic,
                accountBasic,
                buildId,
                fixture(),
                fixture(),
            )

            val expected = PagedData(
                data = listOf(artifact, artifact),
                nextCursor = response.paging?.nextCursor.orEmpty(),
            )
            assertEquals(expected, pagedData)
        }

    @Test
    fun `given artifact id to download when get download data then verify download url`() =
        runTest {
            val downloadUrl = getUrlFixture(fixture)
            val response = ResponseDto(
                data = fixture<ArtifactDetailItemDto>().copy(downloadUrl = downloadUrl.value),
                paging = fixture<PagingDto>(),
                message = fixture(),
            )
            whenever(bitriseEndpoint.getArtifactDetail(any(), any(), any())).thenReturn(response)

            val downloadData = subject.getArtifactDownloadUrl(
                projectBasic,
                accountBasic,
                buildId,
                getArtifactIdFixture(fixture),
            )

            assertEquals(downloadUrl, downloadData.url)
        }

    @Test
    fun `given artifact id to download when get download data then verify not headers in download data`() =
        runTest {
            val response = ResponseDto(
                data = fixture<ArtifactDetailItemDto>().copy(
                    downloadUrl = getUrlFixture(fixture).value,
                ),
                paging = fixture<PagingDto>(),
                message = fixture(),
            )
            whenever(bitriseEndpoint.getArtifactDetail(any(), any(), any())).thenReturn(response)

            val downloadData = subject.getArtifactDownloadUrl(
                projectBasic,
                accountBasic,
                buildId,
                getArtifactIdFixture(fixture),
            )

            assertEquals(mapOf<String, String>(), downloadData.headers)
        }
}
