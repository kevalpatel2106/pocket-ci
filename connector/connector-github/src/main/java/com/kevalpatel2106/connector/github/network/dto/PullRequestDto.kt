package com.kevalpatel2106.connector.github.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class PullRequestDto(

    @Json(name = "id")
    val id: String,

    @Json(name = "number")
    val number: Long,

    @Json(name = "head")
    val head: BaseCommitDto,

    @Json(name = "base")
    val base: BaseCommitDto,
)
