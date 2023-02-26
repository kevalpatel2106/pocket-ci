package com.kevalpatel2106.connector.bitrise.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class BuildLogsDto(

    @Json(name = "is_archived")
    val isArchived: Boolean,

    @Json(name = "expiring_raw_log_url")
    val rawLogUrl: String?,
)
