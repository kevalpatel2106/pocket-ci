package com.kevalpatel2106.connector.github.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StepDto(

    @Json(name = "conclusion")
    val conclusion: String? = null,

    @Json(name = "name")
    val name: String,

    @Json(name = "status")
    val status: String,
)
