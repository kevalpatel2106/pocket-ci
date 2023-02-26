package com.kevalpatel2106.connector.bitrise.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ResponseDto<T>(
    @Json(name = "data")
    val data: T?,

    @Json(name = "paging")
    val paging: PagingDto?,

    @Json(name = "message")
    val message: String?,
)
