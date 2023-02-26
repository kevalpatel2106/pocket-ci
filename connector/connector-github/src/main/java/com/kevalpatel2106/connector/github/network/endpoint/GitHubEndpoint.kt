package com.kevalpatel2106.connector.github.network.endpoint

import com.kevalpatel2106.connector.github.network.dto.ArtifactDto
import com.kevalpatel2106.connector.github.network.dto.ArtifactListResponseDto
import com.kevalpatel2106.connector.github.network.dto.BuildListDto
import com.kevalpatel2106.connector.github.network.dto.JobListDto
import com.kevalpatel2106.connector.github.network.dto.ProjectDto
import com.kevalpatel2106.connector.github.network.dto.UserDto
import com.kevalpatel2106.connector.github.network.interceptor.AuthHeaderInterceptor.Companion.ADD_AUTH_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

internal interface GitHubEndpoint {

    @Headers(ADD_AUTH_HEADER, API_VERSION_HEADER)
    @GET("user")
    suspend fun getUser(): UserDto

    @Headers(ADD_AUTH_HEADER, API_VERSION_HEADER)
    @GET("user/repos")
    suspend fun getProjectsUpdatedDesc(
        @Query("page") page: Int,
        @Query("visibility") visibility: String = "all",
        @Query("sort") sort: String = SORT_BY_UPDATED,
        @Query("direction") direction: String = SORT_DIRECTION_DESC,
        @Query("per_page") perPage: Int,
    ): List<ProjectDto>

    @Headers(ADD_AUTH_HEADER, API_VERSION_HEADER)
    @GET("repos/{owner}/{repo}/actions/runs")
    suspend fun getBuildsTriggeredAtDesc(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): BuildListDto

    @Headers(ADD_AUTH_HEADER, API_VERSION_HEADER)
    @GET("/repos/{owner}/{repo}/actions/runs/{run_id}/jobs")
    suspend fun getJobs(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("run_id") buildId: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): JobListDto

    @Headers(ADD_AUTH_HEADER, API_VERSION_HEADER)
    @GET("/repos/{owner}/{repo}/actions/runs/{run_id}/artifacts")
    suspend fun getArtifacts(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("run_id") buildId: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): ArtifactListResponseDto

    @Headers(ADD_AUTH_HEADER, API_VERSION_HEADER)
    @GET("/repos/{owner}/{repo}/actions/jobs/{job_id}/logs")
    suspend fun getJobLogs(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("job_id") jobId: String,
    ): String

    @Headers(ADD_AUTH_HEADER, API_VERSION_HEADER)
    @GET("/repos/{owner}/{repo}/actions/artifacts/{artifact_id}")
    suspend fun getArtifactDetail(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("artifact_id") artifactId: String,
    ): ArtifactDto

    companion object {
        private const val API_VERSION = "v3"
        internal const val ACCEPT_HEADER_KEY = "Accept"
        internal const val ACCEPT_HEADER_VALUE = "application/vnd.github.$API_VERSION+json"
        private const val API_VERSION_HEADER = "$ACCEPT_HEADER_KEY: $ACCEPT_HEADER_VALUE"
        private const val ADD_AUTH_HEADER = "$ADD_AUTH_KEY: true"

        private const val SORT_DIRECTION_DESC = "desc"
        private const val SORT_BY_UPDATED = "updated"
        const val FIRST_PAGE_CURSOR = 1
    }
}
