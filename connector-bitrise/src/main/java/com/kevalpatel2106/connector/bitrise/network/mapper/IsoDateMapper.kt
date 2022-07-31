package com.kevalpatel2106.connector.bitrise.network.mapper

import java.util.Date

internal interface IsoDateMapper {
    operator fun invoke(isoTime: String?): Date?
}
