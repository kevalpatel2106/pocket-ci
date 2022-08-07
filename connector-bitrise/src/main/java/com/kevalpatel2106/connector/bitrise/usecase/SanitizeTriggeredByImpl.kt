package com.kevalpatel2106.connector.bitrise.usecase

import javax.inject.Inject

internal class SanitizeTriggeredByImpl @Inject constructor() : SanitizeTriggeredBy {

    override operator fun invoke(triggeredBy: String?): String? {
        val last = triggeredBy?.split(SEPARATOR)?.last()
        return if (last.isNullOrBlank()) null else last
    }

    companion object {
        private const val SEPARATOR = "/"
    }
}
