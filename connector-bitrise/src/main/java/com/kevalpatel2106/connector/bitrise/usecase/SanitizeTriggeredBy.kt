package com.kevalpatel2106.connector.bitrise.usecase

interface SanitizeTriggeredBy {
    operator fun invoke(triggeredBy: String?): String?
}
