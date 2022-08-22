package com.kevalpatel2106.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.net.MalformedURLException
import java.net.URL

@Parcelize
data class Url(val value: String) : Parcelable {

    init {
        if (!isValidUrl(value)) throw MalformedURLException("Invalid url $value")
    }

    companion object {
        val EMPTY = Url("")

        internal fun isValidUrl(value: String) = try {
            URL(value)
            true
        } catch (e: MalformedURLException) {
            value.isBlank()
        }
    }
}

fun String.toUrl() = Url(this)

fun String?.toUrlOrNull() = if (isNullOrBlank()) {
    null
} else if (Url.isValidUrl(this)) {
    Url(this)
} else {
    null
}
