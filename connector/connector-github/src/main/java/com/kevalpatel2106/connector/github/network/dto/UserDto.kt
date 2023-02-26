package com.kevalpatel2106.connector.github.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class UserDto(
    @Json(name = "id")
    val id: Long,

    @Json(name = "bio")
    val bio: String? = null,

    @Json(name = "login")
    val login: String,

    @Json(name = "company")
    val company: String? = null,

    @Json(name = "email")
    val email: String,

    @Json(name = "avatar_url")
    val avatarUrl: String? = null,

    @Json(name = "name")
    val name: String,
)
