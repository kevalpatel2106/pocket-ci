package com.kevalpatel2106.connector.github.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArtifactDto(

    @Json(name = "name")
    val name: String,

    @Json(name = "size_in_bytes")
    val sizeInBytes: Long,

    @Json(name = "created_at")
    val createdAt: String? = null,

    @Json(name = "archive_download_url")
    val downloadUrl: String,

    @Json(name = "id")
    val id: Long,
)
