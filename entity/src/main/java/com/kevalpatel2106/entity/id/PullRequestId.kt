package com.kevalpatel2106.entity.id

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PullRequestId(val value: String) : Parcelable {

    init {
        assert(value.isNotBlank()) { "PR id cannot be blank value!" }
    }
}

fun String?.toPullRequestIdOrNull() = if (this != null) PullRequestId(this) else null
fun String.toPullRequestId() = PullRequestId(this)
