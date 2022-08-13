package com.kevalpatel2106.connector.github.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class JobListDto(
    @Json(name = "total_count")
    val totalCount: Int,

    @Json(name = "jobs")
    val jobs: List<JobDto>,
)
