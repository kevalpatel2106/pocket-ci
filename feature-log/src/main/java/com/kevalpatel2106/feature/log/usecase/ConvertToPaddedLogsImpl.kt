package com.kevalpatel2106.feature.log.usecase

import javax.inject.Inject

internal class ConvertToPaddedLogsImpl @Inject constructor() : ConvertToPaddedLogs {
    override fun invoke(logs: String) = "\n\n$logs\n\n\n\n"
}
