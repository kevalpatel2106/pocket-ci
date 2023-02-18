package com.kevalpatel2106.connector.github

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.github.network.GitHubRetrofitClient
import com.kevalpatel2106.connector.github.network.dto.ArtifactDto
import com.kevalpatel2106.connector.github.network.dto.ArtifactListResponseDto
import com.kevalpatel2106.connector.github.network.endpoint.GitHubEndpoint
import com.kevalpatel2106.connector.github.network.interceptor.AuthHeaderInterceptor.Companion.AUTHENTICATION
import com.kevalpatel2106.connector.github.network.mapper.ArtifactListItemMapper
import com.kevalpatel2106.connector.github.usecase.TokenHeaderValueBuilder
import com.kevalpatel2106.coreTest.buildHttpException
import com.kevalpatel2106.coreTest.getAccountBasicFixture
import com.kevalpatel2106.coreTest.getArtifactFixture
import com.kevalpatel2106.coreTest.getArtifactIdFixture
import com.kevalpatel2106.coreTest.getBuildIdFixture
import com.kevalpatel2106.coreTest.getProjectBasicFixture
import com.kevalpatel2106.coreTest.getUrlFixture
import com.kevalpatel2106.entity.Artifact
import com.kevalpatel2106.entity.ArtifactDownloadData
import com.kevalpatel2106.entity.PagedData
import com.kevalpatel2106.entity.Url
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

internal class GithubArtifactProviderTest {
    private val mappedArtifact = getArtifactFixture(fixture)
    private val projectBasic = getProjectBasicFixture(fixture)
    private val accountBasic = getAccountBasicFixture(fixture)
    private val buildId = getBuildIdFixture(fixture)

    private val githubEndpoint = mock<GitHubEndpoint>()
    private val retrofitClient = mock<GitHubRetrofitClient> {
        on { getGithubService(any(), any()) } doReturn githubEndpoint
    }
    private val artifactListItemMapper = mock<ArtifactListItemMapper> {
        on { invoke(any(), any()) } doReturn mappedArtifact
    }
    private val tokenHeaderValueBuilder = mock<TokenHeaderValueBuilder>()

    private val subject = GithubArtifactProvider(
        retrofitClient,
        artifactListItemMapper,
        tokenHeaderValueBuilder,
    )

    // region getArtifacts
    @ParameterizedTest(
        name = "given cursor {0} when artifacts page api call made then verify api call parameters with page number {1}"
    )
    @MethodSource("provideValuesForApiParameterTest")
    fun `given cursor when artifacts page api call made then verify api call parameters`(
        inputCursor: String?,
        expectedPageNumber: Int,
    ) = runTest {
        val limit = fixture<Int>()
        whenever(githubEndpoint.getArtifacts(any(), any(), any(), any(), any()))
            .thenReturn(fixture())

        subject.getArtifacts(projectBasic, accountBasic, buildId, inputCursor, limit)

        verify(githubEndpoint).getArtifacts(
            projectBasic.owner,
            projectBasic.name,
            buildId.getValue(),
            limit,
            expectedPageNumber,
        )
    }

    @ParameterizedTest(
        name = "given current cursor {0} and response artifact list when artifacts page api call made then verify next page cursor {2}"
    )
    @MethodSource("provideValuesForNextCursorTest")
    fun `given current cursor and response artifact list when artifacts page api call made then verify next page cursor`(
        currentCursor: String?,
        responseArtifacts: List<ArtifactDto>,
        expectedNextCursor: String?,
    ) = runTest {
        whenever(githubEndpoint.getArtifacts(any(), any(), any(), any(), any()))
            .thenReturn(ArtifactListResponseDto(fixture(), responseArtifacts))

        val data =
            subject.getArtifacts(projectBasic, accountBasic, buildId, currentCursor, fixture())

        assertEquals(expectedNextCursor, data.nextCursor)
    }

    @Test
    fun `given non-empty response artifact list when artifacts page api call made then verify paged data`() =
        runTest {
            val artifactDtos = listOf<ArtifactDto>(fixture(), fixture())
            whenever(githubEndpoint.getArtifacts(any(), any(), any(), any(), any()))
                .thenReturn(ArtifactListResponseDto(fixture(), artifactDtos))

            val data =
                subject.getArtifacts(projectBasic, accountBasic, buildId, fixture(), fixture())

            assertEquals(listOf(mappedArtifact, mappedArtifact), data.data)
        }

    @Test
    fun `given empty response artifact list when artifacts page api call made then verify paged data`() =
        runTest {
            whenever(githubEndpoint.getArtifacts(any(), any(), any(), any(), any()))
                .thenReturn(ArtifactListResponseDto(fixture(), listOf()))

            val data =
                subject.getArtifacts(projectBasic, accountBasic, buildId, fixture(), fixture())

            assertEquals(PagedData(listOf<Artifact>(), null), data)
        }

    @Test
    fun `given error getting artifacts when artifacts page loads then exception thrown`() =
        runTest {
            whenever(githubEndpoint.getArtifacts(any(), any(), any(), any(), any()))
                .thenThrow(IllegalStateException())

            assertThrows<IllegalStateException> {
                subject.getArtifacts(projectBasic, accountBasic, buildId, fixture(), fixture())
            }
        }
    // endregion

    // region getArtifactDownloadUrl
    @Test
    fun `given artifact download url can be fetched from CI when get download url called then verify result`() =
        runTest {
            val testDownloadUrl = getUrlFixture(fixture)
            val testAuthToken = fixture<String>()
            whenever(githubEndpoint.getArtifactDetail(any(), any(), any()))
                .thenReturn(fixture<ArtifactDto>().copy(downloadUrl = testDownloadUrl.value))
            whenever(tokenHeaderValueBuilder(any())).thenReturn(testAuthToken)

            val actual = subject.getArtifactDownloadUrl(
                projectBasic,
                accountBasic,
                buildId,
                getArtifactIdFixture(fixture),
            )

            val expected = ArtifactDownloadData(
                url = testDownloadUrl,
                headers = mapOf(AUTHENTICATION to testAuthToken),
            )
            assertEquals(expected, actual)
        }

    @Test
    fun `given 410 code returned from CI when get download url called then download data with empty url returned`() =
        runTest {
            val testAuthToken = fixture<String>()
            whenever(githubEndpoint.getArtifactDetail(any(), any(), any()))
                .thenThrow(buildHttpException(HTTP_GONE, fixture))
            whenever(tokenHeaderValueBuilder(any())).thenReturn(testAuthToken)

            val actual = subject.getArtifactDownloadUrl(
                projectBasic,
                accountBasic,
                buildId,
                getArtifactIdFixture(fixture),
            )

            val expected = ArtifactDownloadData(
                url = Url.EMPTY,
                headers = mapOf(AUTHENTICATION to testAuthToken),
            )
            assertEquals(expected, actual)
        }

    @Test
    fun `given 500 code returned from CI when get download url called then verify error thrown`() =
        runTest {
            whenever(githubEndpoint.getArtifactDetail(any(), any(), any()))
                .thenThrow(buildHttpException(HTTP_INTERNAL_ERROR, fixture))

            val error = assertThrows<HttpException> {
                subject.getArtifactDownloadUrl(
                    projectBasic,
                    accountBasic,
                    buildId,
                    getArtifactIdFixture(fixture),
                )
            }
            assertEquals(HTTP_INTERNAL_ERROR, error.code())
        }

    @Test
    fun `given generic exception occurred when get download url called then verify error thrown`() =
        runTest {
            whenever(githubEndpoint.getArtifactDetail(any(), any(), any()))
                .thenThrow(IllegalStateException())

            assertThrows<IllegalStateException> {
                subject.getArtifactDownloadUrl(
                    projectBasic,
                    accountBasic,
                    buildId,
                    getArtifactIdFixture(fixture),
                )
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
            // Format current page, response artifact list, next cursor
            arguments(null, listOf<ArtifactDto>(), null),
            arguments(null, listOf<ArtifactDto>(fixture(), fixture()), "2"),

            arguments("invalid-cursor", listOf<ArtifactDto>(), null),
            arguments("invalid-cursor", listOf<ArtifactDto>(fixture(), fixture()), "2"),

            arguments("100", listOf<ArtifactDto>(), null),
            arguments("100", listOf<ArtifactDto>(fixture(), fixture()), "101"),
        )
    }
}
