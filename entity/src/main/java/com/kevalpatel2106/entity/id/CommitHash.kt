package com.kevalpatel2106.entity.id

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommitHash(private val value: String) : Parcelable {

    @IgnoredOnParcel
    val shortHash: String
        get() = value.substring(
            startIndex = value.length - SHORT_COMMIT_HASH_LENGTH,
            endIndex = value.length,
        )

    init {
        assert(value.length >= SHORT_COMMIT_HASH_LENGTH && value.isNotBlank()) {
            "Commit hash ($value) cannot be blank value!"
        }
    }

    fun getValue() = value

    companion object {
        private const val SHORT_COMMIT_HASH_LENGTH = 8
    }
}
