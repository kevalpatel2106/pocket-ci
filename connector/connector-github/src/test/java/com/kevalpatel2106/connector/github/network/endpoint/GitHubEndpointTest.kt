package com.kevalpatel2106.connector.github.network.endpoint

import com.flextrade.kfixture.KFixture
import com.kevalpatel2106.connector.github.network.GitHubRetrofitClientImpl
import com.kevalpatel2106.connector.github.network.endpoint.responses.GetArtifactResponses
import com.kevalpatel2106.connector.github.network.endpoint.responses.GetArtifactsResponses
import com.kevalpatel2106.connector.github.network.endpoint.responses.GetBuildsResponses
import com.kevalpatel2106.connector.github.network.endpoint.responses.GetJobsResponses
import com.kevalpatel2106.connector.github.network.endpoint.responses.GetLogsResponses
import com.kevalpatel2106.connector.github.network.endpoint.responses.GetRepositoriesResponses
import com.kevalpatel2106.connector.github.network.endpoint.responses.GetUserResponses
import com.kevalpatel2106.connector.github.usecase.TokenHeaderValueBuilder
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
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import retrofit2.HttpException
import java.net.HttpURLConnection

internal class GitHubEndpointTest {
    private val fixture = KFixture()
    private val flavouredInterceptor = mock<FlavouredInterceptor> {
        on { getInterceptors() } doReturn listOf()
        on { getNetworkInterceptors() } doReturn listOf()
    }
    private val mockOkHttpClientFactory = mock<OkHttpClientFactory> {
        on { getFlavouredInterceptor() } doReturn flavouredInterceptor
        on { getOkHttpClient() } doReturn OkHttpClient()
    }
    private val tokenHeaderValueBuilder = mock<TokenHeaderValueBuilder> {
        on { invoke(any()) } doReturn fixture()
    }
    private lateinit var gitHubEndpoint: GitHubEndpoint

    @BeforeEach
    fun beforeEach() {
        gitHubEndpoint = GitHubRetrofitClientImpl(
            mockOkHttpClientFactory,
            tokenHeaderValueBuilder,
        ).getGithubService(server.url("/v3/").toString().toUrl(), fixture())
    }

    @Test
    fun `test GET user endpoint success`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetUserResponses.SUCCESS)

        val user = gitHubEndpoint.getUser()

        assertEquals(GetUserResponses.id, user.id)
        assertEquals(GetUserResponses.login, user.login)
        assertEquals(GetUserResponses.email, user.email)
    }

    @Test
    fun `test GET user endpoint unauthorised`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_UNAUTHORIZED, GetUserResponses.UNAUTHORISED)

        val error = assertThrows<HttpException> { gitHubEndpoint.getUser() }
        assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, error.code())
    }

    @Test
    fun `test GET repositories endpoint success`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetRepositoriesResponses.SUCCESS)

        val projects = gitHubEndpoint.getProjectsUpdatedDesc(page = 1, perPage = 1)

        assertEquals(2, projects.size)
        assertEquals(GetRepositoriesResponses.projectOwner, projects.first().owner.login)
        assertEquals(GetRepositoriesResponses.project, projects.first().name)
    }

    @Test
    fun `test GET repositories endpoint success empty response`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetRepositoriesResponses.EMPTY)

        val projects = gitHubEndpoint.getProjectsUpdatedDesc(page = 1, perPage = 1)

        assertTrue(projects.isEmpty())
    }

    @Test
    fun `test GET repositories endpoint unauthorised`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_UNAUTHORIZED, GetRepositoriesResponses.UNAUTHORISED)

        val error = assertThrows<HttpException> {
            gitHubEndpoint.getProjectsUpdatedDesc(page = 1, perPage = 1)
        }
        assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, error.code())
    }

    @Test
    fun `test GET builds endpoint success`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetBuildsResponses.SUCCESS)

        val builds = gitHubEndpoint.getBuildsTriggeredAtDesc(
            page = 1,
            perPage = 1,
            owner = fixture(),
            repo = fixture(),
        )

        assertEquals(2, builds.workflowRuns.size)
        assertEquals(GetBuildsResponses.workflowId, builds.workflowRuns.first().id)
        assertEquals(GetBuildsResponses.commitHash, builds.workflowRuns.first().headCommit?.id)
        assertEquals(GetBuildsResponses.buildNumber, builds.workflowRuns.first().runNumber)
    }

    @Test
    fun `test GET builds endpoint success empty response`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetBuildsResponses.EMPTY)

        val builds = gitHubEndpoint.getBuildsTriggeredAtDesc(
            page = 1,
            perPage = 1,
            owner = fixture(),
            repo = fixture(),
        )

        assertTrue(builds.workflowRuns.isEmpty())
    }

    @Test
    fun `test GET builds endpoint unauthorised`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_UNAUTHORIZED, GetBuildsResponses.UNAUTHORISED)

        val error = assertThrows<HttpException> {
            gitHubEndpoint.getBuildsTriggeredAtDesc(
                page = 1,
                perPage = 1,
                owner = fixture(),
                repo = fixture(),
            )
        }
        assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, error.code())
    }

    @Test
    fun `test GET jobs endpoint success`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetJobsResponses.SUCCESS)

        val jobs = gitHubEndpoint.getJobs(
            page = 1,
            perPage = 1,
            owner = fixture(),
            repo = fixture(),
            buildId = fixture(),
        )

        assertEquals(1, jobs.jobs.size)
        assertEquals(GetJobsResponses.jobId, jobs.jobs.first().id)
        assertEquals(GetJobsResponses.status, jobs.jobs.first().status)
        assertEquals(GetJobsResponses.conclusion, jobs.jobs.first().conclusion)

        assertEquals(10, jobs.jobs.first().steps.size)
        assertEquals(GetJobsResponses.lastStepName, jobs.jobs.first().steps.last().name)
    }

    @Test
    fun `test GET jobs endpoint success empty response`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetJobsResponses.EMPTY)

        val jobs = gitHubEndpoint.getJobs(
            page = 1,
            perPage = 1,
            owner = fixture(),
            repo = fixture(),
            buildId = fixture(),
        )

        assertTrue(jobs.jobs.isEmpty())
    }

    @Test
    fun `test GET jobs endpoint unauthorised`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_UNAUTHORIZED, GetJobsResponses.UNAUTHORISED)

        val error = assertThrows<HttpException> {
            gitHubEndpoint.getJobs(
                page = 1,
                perPage = 1,
                owner = fixture(),
                repo = fixture(),
                buildId = fixture(),
            )
        }
        assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, error.code())
    }

    @Test
    fun `test GET artifacts endpoint success`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetArtifactsResponses.SUCCESS)

        val artifacts = gitHubEndpoint.getArtifacts(
            page = 1,
            perPage = 1,
            owner = fixture(),
            repo = fixture(),
            buildId = fixture(),
        )

        assertEquals(2, artifacts.artifacts.size)
        assertEquals(GetArtifactsResponses.artifact1Id, artifacts.artifacts.first().id)
        assertEquals(GetArtifactsResponses.artifact1Name, artifacts.artifacts.first().name)
        assertEquals(GetArtifactsResponses.artifact1Size, artifacts.artifacts.first().sizeInBytes)
        assertEquals(GetArtifactsResponses.artifact2Id, artifacts.artifacts[1].id)
        assertEquals(GetArtifactsResponses.artifact2Name, artifacts.artifacts[1].name)
        assertEquals(GetArtifactsResponses.artifact2Size, artifacts.artifacts[1].sizeInBytes)
    }

    @Test
    fun `test GET artifacts endpoint success empty response`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetArtifactsResponses.EMPTY)

        val artifacts = gitHubEndpoint.getArtifacts(
            page = 1,
            perPage = 1,
            owner = fixture(),
            repo = fixture(),
            buildId = fixture(),
        )

        assertTrue(artifacts.artifacts.isEmpty())
    }

    @Test
    fun `test GET artifacts endpoint unauthorised`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_UNAUTHORIZED, GetArtifactsResponses.UNAUTHORISED)

        val error = assertThrows<HttpException> {
            gitHubEndpoint.getArtifacts(
                page = 1,
                perPage = 1,
                owner = fixture(),
                repo = fixture(),
                buildId = fixture(),
            )
        }
        assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, error.code())
    }

    @Test
    fun `test GET artifact endpoint success`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetArtifactResponses.SUCCESS)

        val artifact = gitHubEndpoint.getArtifactDetail(
            owner = fixture(),
            repo = fixture(),
            artifactId = fixture(),
        )

        assertEquals(GetArtifactResponses.artifactId, artifact.id)
        assertEquals(GetArtifactResponses.artifactName, artifact.name)
        assertEquals(GetArtifactResponses.artifactSize, artifact.sizeInBytes)
    }

    @Test
    fun `test GET artifact endpoint unauthorised`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_UNAUTHORIZED, GetArtifactResponses.UNAUTHORISED)

        val error = assertThrows<HttpException> {
            gitHubEndpoint.getArtifactDetail(
                owner = fixture(),
                repo = fixture(),
                artifactId = fixture(),
            )
        }
        assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, error.code())
    }

    @Test
    fun `test GET job logs endpoint found`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetLogsResponses.SUCCESS)

        val log = gitHubEndpoint.getJobLogs(
            owner = fixture(),
            repo = fixture(),
            jobId = fixture(),
        )

        assertEquals(GetLogsResponses.SUCCESS, log)
    }

    @Test
    fun `test GET job logs endpoint empty logs found`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_OK, GetLogsResponses.EMPTY)

        val log = gitHubEndpoint.getJobLogs(
            owner = fixture(),
            repo = fixture(),
            jobId = fixture(),
        )

        assertTrue(log.isEmpty())
    }

    @Test
    fun `test GET job logs endpoint with gone response`() = runTest {
        enqueueResponse(HttpURLConnection.HTTP_GONE, null)

        val error = assertThrows<HttpException> {
            gitHubEndpoint.getJobLogs(
                owner = fixture(),
                repo = fixture(),
                jobId = fixture(),
            )
        }
        assertEquals(HttpURLConnection.HTTP_GONE, error.code())
    }

    private fun enqueueResponse(code: Int, response: String?) {
        server.enqueue(
            MockResponse()
                .setResponseCode(code)
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
