package com.kevalpatel2106.connector.github.network.mapper

import java.text.SimpleDateFormat
import javax.inject.Inject

internal class IsoDateMapperImpl @Inject constructor() : IsoDateMapper {

    override fun invoke(isoTime: String?) = if (isoTime != null) {
        SimpleDateFormat(ISO_8601_TIME_FORMAT).parse(isoTime)
    } else {
        null
    }

    companion object {
        private const val ISO_8601_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    }
}
