package com.kevalpatel2106.entity

import androidx.compose.runtime.Immutable
import java.util.*

@Immutable
data class DisplayError(
    val throwable: Throwable,
    val headline: String,
    val message: String,

    // Debugging info
    val time: Date,
    val technicalMessage: String?,
    val nonRecoverable: Boolean,
    val unableToTriage: Boolean,
    val url: String? = null,
    val httpResponseCode: Int = UNKNOWN_HTTP_CODE,
    val httpResponse: String? = null,
) {

    override fun toString(): String {
        return buildString {
            append("User display information:\n")
            append("Time: $time\n")
            append("Title: $headline\n")
            append("Message: $message\n")
            append("\n")
            append("Detailed message:\n$technicalMessage\n")
            append("Stacktrace:\n${throwable.stackTraceToString()}\n")
            append("\n")
            append("HTTP Error information:\n")
            append("URL: $url\n")
            append("Response code: $httpResponseCode\n")
            append("Response: $httpResponse\n")
            append("\n")
            append("Other info:\n")
            append("Triaged: ${!unableToTriage}\n")
            append("Can recover: ${!nonRecoverable}\n")
            append("\n")
        }
    }

    companion object {
        const val UNKNOWN_HTTP_CODE = -1001
    }
}
