package com.kevalpatel2106.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Token internal constructor(private val value: String) : Parcelable {

    init {
        assert(value.isNotBlank()) { "Token cannot be blank value!" }
    }

    fun getValue() = value
}

fun String?.toTokenOrNull() = if (this != null) Token(this) else null
fun String.toToken() = Token(this)
