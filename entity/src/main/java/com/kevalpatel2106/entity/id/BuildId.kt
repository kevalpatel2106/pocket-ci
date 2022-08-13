package com.kevalpatel2106.entity.id

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BuildId(private val value: String) : Parcelable {

    init {
        assert(value.isNotBlank()) { "Build ID cannot be blank value!" }
    }

    fun getValue() = value
}

fun String?.toBuildIdIdOrNull() = if (!this.isNullOrBlank()) BuildId(this) else null
fun String.toBuildId() = BuildId(this)
