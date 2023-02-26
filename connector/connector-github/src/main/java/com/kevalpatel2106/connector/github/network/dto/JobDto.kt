package com.kevalpatel2106.connector.github.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JobDto(

    @Json(name = "id")
    val id: Long,

    @Json(name = "name")
    val name: String,

    @Json(name = "started_at")
    val startedAt: String,

    @Json(name = "completed_at")
    val completedAt: String? = null,

    @Json(name = "conclusion")
    val conclusion: String? = null,

    @Json(name = "status")
    val status: String,

    @Json(name = "steps")
    val steps: List<StepDto>,
)
