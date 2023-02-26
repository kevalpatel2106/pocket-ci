package com.kevalpatel2106.connector.bitrise.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class PagingDto(
    @Json(name = "total_item_count")
    val total: Int,

    @Json(name = "page_item_limit")
    val limit: Int,

    @Json(name = "next")
    val nextCursor: String?,
)
