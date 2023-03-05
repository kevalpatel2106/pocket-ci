package com.kevalpatel2106.feature.log.usecase

import kotlinx.collections.immutable.toPersistentList
import javax.inject.Inject

internal class ConvertToPaddedLogsImpl @Inject constructor() : ConvertToPaddedLogs {
    override suspend fun invoke(logs: String) = if (logs.isBlank()) {
        emptyList()
    } else {
        mutableListOf<String>().apply {
            addAll(logs.split("\n"))
            add("\n")
            add("\n")
        }
    }.toPersistentList()
}
