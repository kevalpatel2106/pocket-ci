package com.kevalpatel2106.connector.bitrise.network.mapper

import java.text.SimpleDateFormat
import java.util.TimeZone
import javax.inject.Inject

internal class IsoDateMapperImpl @Inject constructor() : IsoDateMapper {
    private val sdf = SimpleDateFormat(ISO_8601_TIME_FORMAT)
        .apply { timeZone = TimeZone.getTimeZone("UTC") }

    override fun invoke(isoTime: String?) = if (isoTime.isNullOrBlank()) {
        null
    } else {
        sdf.parse(isoTime)
    }

    companion object {
        private const val ISO_8601_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    }
}
