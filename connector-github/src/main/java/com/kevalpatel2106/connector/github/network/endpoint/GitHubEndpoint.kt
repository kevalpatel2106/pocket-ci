package com.kevalpatel2106.connector.github.network.endpoint

import com.kevalpatel2106.connector.github.network.dto.BuildListDto
import com.kevalpatel2106.connector.github.network.dto.ProjectDto
import com.kevalpatel2106.connector.github.network.dto.UserDto
import com.kevalpatel2106.connector.github.network.interceptor.HeadersInterceptor
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

internal interface GitHubEndpoint {

    @Headers("${HeadersInterceptor.ADD_AUTH}: true")
    @GET("user")
    suspend fun getUser(): UserDto

    @Headers("${HeadersInterceptor.ADD_AUTH}: true")
    @GET("user/repos")
    suspend fun getProjectsUpdatedDesc(
        @Query("page") page: Int,
        @Query("visibility") visibility: String = "all",
        @Query("sort") sort: String = SORT_BY_UPDATED,
        @Query("direction") direction: String = SORT_DIRECTION_DESC,
        @Query("per_page") perPage: Int,
    ): List<ProjectDto>

    @Headers("${HeadersInterceptor.ADD_AUTH}: true")
    @GET("repos/{login}/{repo}/actions/runs")
    suspend fun getBuildsTriggeredAtDesc(
        @Path("login") login: String,
        @Path("repo") repo: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): BuildListDto

    companion object {
        private const val SORT_DIRECTION_DESC = "desc"
        private const val SORT_BY_UPDATED = "updated"
        const val FIRST_PAGE_CURSOR = 1
    }
}
