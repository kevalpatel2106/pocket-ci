package com.kevalpatel2106.connector.github.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ProjectDto(
    @Json(name = "id")
    val id: Int,

    @Json(name = "visibility")
    val visibility: String?,

    @Json(name = "full_name")
    val fullName: String,

    @Json(name = "name")
    val name: String,

    @Json(name = "description")
    val description: String?,

    @Json(name = "html_url")
    val htmlUrl: String,

    @Json(name = "updated_at")
    val updatedAt: String?,

    @Json(name = "disabled")
    val disabled: Boolean = false,

    @Json(name = "owner")
    val owner: BaseUserDto,
)
