package com.kevalpatel2106.entity.id

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommitHash(private val value: String) : Parcelable {

    @IgnoredOnParcel
    val shortHash: String = value.substring(value.length - SHORT_COMMIT_HASH_LENGTH, value.length)

    init {
        assert(value.isNotBlank()) { "Commit hash cannot be blank value!" }
    }

    fun getValue() = value

    companion object {
        private const val SHORT_COMMIT_HASH_LENGTH = 8
    }
}

fun String?.toCommitHashOrNull() = if (!this.isNullOrBlank()) CommitHash(this) else null
fun String.toCommitHash() = CommitHash(this)
