package com.kevalpatel2106.entity.id

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PullRequestId(private val value: String) : Parcelable {

    init {
        assert(value.isNotBlank()) { "PR id cannot be blank value!" }
    }

    fun getValue() = value
}

fun String?.toPullRequestIdOrNull() = if (!this.isNullOrBlank()) PullRequestId(this) else null
fun String.toPullRequestId() = PullRequestId(this)
