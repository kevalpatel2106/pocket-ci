package com.kevalpatel2106.entity.id

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommitHash(val value: String) : Parcelable {

    init {
        assert(value.isNotBlank()) { "Commit hash cannot be blank value!" }
    }
}

fun String?.toCommitHashOrNull() = if (this != null) CommitHash(this) else null
fun String.toCommitHash() = CommitHash(this)
