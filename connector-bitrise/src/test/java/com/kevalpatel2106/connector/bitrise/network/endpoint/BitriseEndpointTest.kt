package com.kevalpatel2106.connector.bitrise.network.endpoint

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.bitrise.network.BitriseRetrofitClientImpl
import com.kevalpatel2106.connector.bitrise.network.endpoint.responses.GetArtifactResponses
import com.kevalpatel2106.connector.bitrise.network.endpoint.responses.GetArtifactsResponses
import com.kevalpatel2106.connector.bitrise.network.endpoint.responses.GetBuildsResponses
import com.kevalpatel2106.connector.bitrise.network.endpoint.responses.GetLogsResponses
import com.kevalpatel2106.connector.bitrise.network.endpoint.responses.GetRepositoriesResponses
import com.kevalpatel2106.connector.bitrise.network.endpoint.responses.GetUserResponses
import com.kevalpatel2106.connector.bitrise.network.interceptor.VersionNameInterceptor
import com.kevalpatel2106.coreNetwork.okHttp.FlavouredInterceptor
import com.kevalpatel2106.coreNetwork.okHttp.OkHttpClientFactory
import com.kevalpatel2106.entity.toUrl
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import retrofit2.HttpException
import java.net.HttpURLConnection

internal class BitriseEndpointTest {
    private val fixture = KFixture()
    private val flavouredInterceptor = mock<FlavouredInterceptor> {
        on { getInterceptors() } doReturn listOf()
        on { getNetworkInterceptors() } doReturn listOf()
    }
    private val mockOkHttpClientFactory = mock<OkHttpClientFactory> {
        on { getFlavouredInterceptor() } doReturn flavouredInterceptor
        on { getOkHttpClient() } doReturn OkHttpClient()
    }
    private lateinit var bitriseEndpoint: BitriseEndpoint

    @BeforeEach
    fun beforeEach() {
        bitriseEndpoint = BitriseRetrofitClientImpl(mockOkHttpClientFactory)
            .getBitriseService(server.url("/").toString().toUrl(), fixture())
    }

    @Test
    fun `test GET user endpoint success`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetUserResponses.SUCCESS)

        val user = bitriseEndpoint.getMe().data

        assertEquals(GetUserResponses.slug, user?.slug)
        assertEquals(GetUserResponses.username, user?.username)
        assertEquals(GetUserResponses.email, user?.email)
    }

    @Test
    fun `test GET user endpoint unauthorised`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_UNAUTHORIZED, GetUserResponses.UNAUTHORISED)

        val error = assertThrows<HttpException> { bitriseEndpoint.getMe() }
        assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, error.code())
    }

    @Test
    fun `test GET repositories endpoint success`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetRepositoriesResponses.SUCCESS)

        val projects = bitriseEndpoint.getProjectsLastBuildAt(next = fixture(), limit = 1).data

        assertEquals(2, projects?.size)
        assertEquals(GetRepositoriesResponses.projectOwner, projects?.first()?.repoOwner)
        assertEquals(GetRepositoriesResponses.project, projects?.first()?.title)
    }

    @Test
    fun `test GET repositories endpoint success empty response`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetRepositoriesResponses.EMPTY)

        val projects = bitriseEndpoint.getProjectsLastBuildAt(next = fixture(), limit = 1).data

        assertTrue(projects?.size == 0)
    }

    @Test
    fun `test GET repositories endpoint unauthorised`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_UNAUTHORIZED, GetRepositoriesResponses.UNAUTHORISED)

        val error = assertThrows<HttpException> {
            bitriseEndpoint.getProjectsLastBuildAt(next = fixture(), limit = 1)
        }
        assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, error.code())
    }

    @Test
    fun `test GET builds endpoint success`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetBuildsResponses.SUCCESS)

        val builds = bitriseEndpoint.getBuildsByCreatedAt(
            appSlug = fixture(),
            limit = 1,
            next = fixture(),
        ).data

        assertEquals(2, builds?.size)
        assertEquals(GetBuildsResponses.workflowId, builds?.first()?.slug)
        assertEquals(GetBuildsResponses.commitHash, builds?.first()?.commitHash)
        assertEquals(GetBuildsResponses.buildNumber, builds?.first()?.buildNumber)
    }

    @Test
    fun `test GET builds endpoint success empty response`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetBuildsResponses.EMPTY)

        val builds = bitriseEndpoint.getBuildsByCreatedAt(
            appSlug = fixture(),
            limit = 1,
            next = fixture(),
        ).data

        assertTrue(builds?.size == 0)
    }

    @Test
    fun `test GET builds endpoint unauthorised`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_UNAUTHORIZED, GetBuildsResponses.UNAUTHORISED)

        val error = assertThrows<HttpException> {
            bitriseEndpoint.getBuildsByCreatedAt(
                appSlug = fixture(),
                limit = 1,
                next = fixture(),
            )
        }
        assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, error.code())
    }

    @Test
    fun `test GET artifacts endpoint success`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetArtifactsResponses.SUCCESS)

        val artifacts = bitriseEndpoint.getArtifacts(
            limit = 1,
            appSlug = fixture(),
            buildSlug = fixture(),
            next = fixture(),
        ).data

        assertEquals(2, artifacts?.size)
        assertEquals(GetArtifactsResponses.artifact1Id, artifacts?.first()?.slug)
        assertEquals(GetArtifactsResponses.artifact1Name, artifacts?.first()?.title)
        assertEquals(GetArtifactsResponses.artifact1Size, artifacts?.first()?.fileSizeBytes)
        assertEquals(GetArtifactsResponses.artifact2Id, artifacts?.get(1)?.slug)
        assertEquals(GetArtifactsResponses.artifact2Name, artifacts?.get(1)?.title)
        assertEquals(GetArtifactsResponses.artifact2Size, artifacts?.get(1)?.fileSizeBytes)
    }

    @Test
    fun `test GET artifacts endpoint success empty response`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetArtifactsResponses.EMPTY)

        val artifacts = bitriseEndpoint.getArtifacts(
            limit = 1,
            appSlug = fixture(),
            buildSlug = fixture(),
            next = fixture(),
        ).data

        assertTrue(artifacts?.size == 0)
    }

    @Test
    fun `test GET artifacts endpoint unauthorised`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_UNAUTHORIZED, GetArtifactsResponses.UNAUTHORISED)

        val error = assertThrows<HttpException> {
            bitriseEndpoint.getArtifacts(
                limit = 1,
                appSlug = fixture(),
                buildSlug = fixture(),
                next = fixture(),
            )
        }
        assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, error.code())
    }

    @Test
    fun `test GET artifact endpoint success`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetArtifactResponses.SUCCESS_ARCHIVED)

        val artifact = bitriseEndpoint.getArtifactDetail(
            appSlug = fixture(),
            buildSlug = fixture(),
            artifactSlug = fixture(),
        ).data

        assertEquals(GetArtifactResponses.artifactId, artifact?.slug)
        assertEquals(GetArtifactResponses.artifactName, artifact?.title)
        assertEquals(GetArtifactResponses.artifactSize, artifact?.fileSizeBytes)
    }

    @Test
    fun `test GET artifact endpoint unauthorised`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_UNAUTHORIZED, GetArtifactResponses.UNAUTHORISED)

        val error = assertThrows<HttpException> {
            bitriseEndpoint.getArtifactDetail(
                appSlug = fixture(),
                buildSlug = fixture(),
                artifactSlug = fixture(),
            )
        }
        assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, error.code())
    }

    @Test
    fun `when archived test GET build logs download endpoint found `() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetLogsResponses.SUCCESS_ARCHIVED)

        val log = bitriseEndpoint.getBuildsLogs(appSlug = fixture(), buildSlug = fixture())

        assertTrue(log.isArchived)
        assertEquals(GetLogsResponses.archivedUrl, log.rawLogUrl)
    }

    @Test
    fun `when not archived test GET build logs endpoint empty logs found`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetLogsResponses.SUCCESS_NOT_ARCHIVED)

        val log = bitriseEndpoint.getBuildsLogs(appSlug = fixture(), buildSlug = fixture())

        assertFalse(log.isArchived)
        assertNull(log.rawLogUrl)
    }

    @Test
    fun `test GET job logs endpoint unauthorised`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_UNAUTHORIZED, GetLogsResponses.UNAUTHORISED)

        val error = assertThrows<HttpException> {
            bitriseEndpoint.getBuildsLogs(appSlug = fixture(), buildSlug = fixture())
        }
        assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, error.code())
    }

    private fun enqueueResponse(code: Int, response: String?) {
        server.enqueue(
            MockResponse()
                .setResponseCode(code)
                .setHeader(
                    VersionNameInterceptor.API_VERSION_HEADER_KEY,
                    VersionNameInterceptor.BITRISE_API_VERSION,
                )
                .apply { response?.let { setBody(it) } },
        )
    }

    companion object {
        private val server = MockWebServer()

        @Before
        fun before() = server.start()

        @After
        fun after() = server.shutdown()
    }
}
