package com.kevalpatel2106.connector.bitrise.network.endpoint

import com.kevalpatel2106.connector.bitrise.network.dto.BuildDto
import com.kevalpatel2106.connector.bitrise.network.dto.MeDto
import com.kevalpatel2106.connector.bitrise.network.dto.ProjectDto
import com.kevalpatel2106.connector.bitrise.network.dto.ResponseDto
import com.kevalpatel2106.connector.bitrise.network.interceptor.AuthHeaderInterceptor
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

internal interface BitriseEndpoint {

    @Headers("${AuthHeaderInterceptor.ADD_AUTH}: true")
    @GET("me")
    suspend fun getMe(): ResponseDto<MeDto>

    @Headers("${AuthHeaderInterceptor.ADD_AUTH}: true")
    @GET("apps")
    suspend fun getProjectsLastBuildAt(
        @Query("sort_by") sortBy: String = SORT_BY_LAST_BUILD_AT,
        @Query("next") next: String?,
        @Query("limit") limit: Int,
    ): ResponseDto<List<ProjectDto>>

    @Headers("${AuthHeaderInterceptor.ADD_AUTH}: true")
    @GET("apps/{app_slug}/builds")
    suspend fun getBuildsByCreatedAt(
        @Path("app_slug") appSlug: String,
        @Query("sort_by") sortBy: String = SORT_BY_CREATED_AT,
        @Query("next") next: String?,
        @Query("limit") limit: Int,
    ): ResponseDto<List<BuildDto>>

    companion object {
        private const val SORT_BY_LAST_BUILD_AT = "last_build_at"
        private const val SORT_BY_CREATED_AT = "created_at"
    }
}
