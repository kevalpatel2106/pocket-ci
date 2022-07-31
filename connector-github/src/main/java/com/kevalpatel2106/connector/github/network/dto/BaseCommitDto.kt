package com.kevalpatel2106.connector.github.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class BaseCommitDto(

    @Json(name = "sha")
    val sha: String,

    @Json(name = "ref")
    val ref: String,
)
