package com.kevalpatel2106.connector.bitrise.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class MeDto(
    @Json(name = "avatar_url")
    val avatarUrl: String? = null,

    @Json(name = "data_id")
    val dataId: Int,

    @Json(name = "created_at")
    val createdAt: String? = null,

    @Json(name = "email")
    val email: String,

    @Json(name = "slug")
    val slug: String? = null,

    @Json(name = "username")
    val username: String,
)
