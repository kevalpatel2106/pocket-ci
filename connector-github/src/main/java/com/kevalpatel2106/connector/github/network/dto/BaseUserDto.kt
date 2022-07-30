package com.kevalpatel2106.connector.github.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class BaseUserDto(
    @Json(name = "login")
    val login: String,

    @Json(name = "id")
    val id: Int,

    @Json(name = "avatar_url")
    val avatarUrl: String? = null,
)
