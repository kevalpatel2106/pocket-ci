package com.kevalpatel2106.connector.github.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class CommitDto(

    @Json(name = "timestamp")
    val timestamp: String,

    @Json(name = "committer")
    val committer: CommitAuthorDto? = null,

    @Json(name = "author")
    val author: CommitAuthorDto,

    @Json(name = "id")
    val id: String,

    @Json(name = "message")
    val message: String? = null,
)
