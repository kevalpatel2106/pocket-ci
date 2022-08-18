package com.kevalpatel2106.feature.log.usecase

internal interface ConvertToPaddedLogs {
    operator fun invoke(logs: String): String
}
