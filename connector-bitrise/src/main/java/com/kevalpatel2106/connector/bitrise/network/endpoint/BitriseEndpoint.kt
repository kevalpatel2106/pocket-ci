package com.kevalpatel2106.connector.bitrise.network.endpoint

import com.kevalpatel2106.connector.bitrise.network.dto.ArtifactListItemDto
import com.kevalpatel2106.connector.bitrise.network.dto.BuildDto
import com.kevalpatel2106.connector.bitrise.network.dto.BuildLogsDto
import com.kevalpatel2106.connector.bitrise.network.dto.MeDto
import com.kevalpatel2106.connector.bitrise.network.dto.ProjectDto
import com.kevalpatel2106.connector.bitrise.network.dto.ResponseDto
import com.kevalpatel2106.connector.bitrise.network.interceptor.AuthHeaderInterceptor.Companion.ADD_AUTH_KEY
import com.kevalpatel2106.connector.bitrise.network.interceptor.VersionNameInterceptor.Companion.API_VERSION_HEADER_KEY
import com.kevalpatel2106.connector.bitrise.network.interceptor.VersionNameInterceptor.Companion.BITRISE_API_VERSION
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

internal interface BitriseEndpoint {

    @Headers(ADD_AUTH_HEADER, API_VERSION_HEADER, ACCEPTS_HEADER)
    @GET("me")
    suspend fun getMe(): ResponseDto<MeDto>

    @Headers(ADD_AUTH_HEADER, API_VERSION_HEADER, ACCEPTS_HEADER)
    @GET("apps")
    suspend fun getProjectsLastBuildAt(
        @Query("sort_by") sortBy: String = SORT_BY_LAST_BUILD_AT,
        @Query("next") next: String?,
        @Query("limit") limit: Int,
    ): ResponseDto<List<ProjectDto>>

    @Headers(ADD_AUTH_HEADER, API_VERSION_HEADER, ACCEPTS_HEADER)
    @GET("apps/{app_slug}/builds")
    suspend fun getBuildsByCreatedAt(
        @Path("app_slug") appSlug: String,
        @Query("sort_by") sortBy: String = SORT_BY_CREATED_AT,
        @Query("next") next: String?,
        @Query("limit") limit: Int,
    ): ResponseDto<List<BuildDto>>

    @Headers(ADD_AUTH_HEADER, API_VERSION_HEADER, ACCEPTS_HEADER)
    @GET("apps/{app_slug}/builds/{build_slug}/log")
    suspend fun getBuildsLogs(
        @Path("app_slug") appSlug: String,
        @Path("build_slug") buildSlug: String,
    ): BuildLogsDto

    @Headers(ADD_AUTH_HEADER, API_VERSION_HEADER, ACCEPTS_HEADER)
    @GET("apps/{app_slug}/builds/{build_slug}/artifacts")
    suspend fun getArtifacts(
        @Path("app_slug") appSlug: String,
        @Path("build_slug") buildSlug: String,
        @Query("next") next: String?,
        @Query("limit") limit: Int,
    ): ResponseDto<List<ArtifactListItemDto>>

    @GET
    suspend fun getRawBuildsLogs(@Url rawUrl: String): String

    companion object {
        private const val ADD_AUTH_HEADER = "$ADD_AUTH_KEY: true"
        private const val API_VERSION_HEADER = "$API_VERSION_HEADER_KEY: $BITRISE_API_VERSION"
        private const val ACCEPTS_HEADER = "Accept: application/json"

        private const val SORT_BY_LAST_BUILD_AT = "last_build_at"
        private const val SORT_BY_CREATED_AT = "created_at"
    }
}
