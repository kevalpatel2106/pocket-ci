package com.kevalpatel2106.connector.github.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class CommitAuthorDto(

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "email")
    val email: String? = null,
)
