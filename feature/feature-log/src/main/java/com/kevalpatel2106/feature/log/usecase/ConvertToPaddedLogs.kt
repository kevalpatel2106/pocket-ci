package com.kevalpatel2106.feature.log.usecase

import kotlinx.collections.immutable.PersistentList

internal interface ConvertToPaddedLogs {
    suspend operator fun invoke(logs: String): PersistentList<String>
}
