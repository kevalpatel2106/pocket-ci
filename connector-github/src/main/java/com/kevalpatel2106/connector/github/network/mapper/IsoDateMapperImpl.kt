package com.kevalpatel2106.connector.github.network.mapper

import java.text.SimpleDateFormat
import java.util.TimeZone
import javax.inject.Inject

internal class IsoDateMapperImpl @Inject constructor() : IsoDateMapper {
    private val sdfWithoutTimeZone = SimpleDateFormat(ISO_8601_TIME_FORMAT_WITHOUT_TIME_ZONE)
        .apply { timeZone = TimeZone.getTimeZone("UTC") }

    private val sdfWithTimeZone = SimpleDateFormat(ISO_8601_TIME_FORMAT_WITH_TIME_ZONE)

    override fun invoke(isoTime: String?) = if (isoTime == null) {
        null
    } else if (isoTime.contains("+")) {
        sdfWithTimeZone.parse(isoTime)
    } else {
        sdfWithoutTimeZone.parse(isoTime)
    }

    companion object {
        private const val ISO_8601_TIME_FORMAT_WITH_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
        private const val ISO_8601_TIME_FORMAT_WITHOUT_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    }
}
