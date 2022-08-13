package com.kevalpatel2106.entity.id

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class JobId(private val value: String) : Parcelable {

    init {
        assert(value.isNotBlank()) { "Job ID cannot be blank value!" }
    }

    fun getValue() = value
}

fun String?.toJobIdOrNull() = if (!this.isNullOrBlank()) JobId(this) else null
fun String.toJobId() = JobId(this)
