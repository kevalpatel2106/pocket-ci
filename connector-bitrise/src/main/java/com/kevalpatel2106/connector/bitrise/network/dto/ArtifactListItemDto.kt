package com.kevalpatel2106.connector.bitrise.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArtifactListItemDto(

    @Json(name = "slug")
    val slug: String,

    @Json(name = "title")
    val title: String,

    @Json(name = "file_size_bytes")
    val fileSizeBytes: Long,
)
