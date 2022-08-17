package com.kevalpatel2106.entity.id

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtifactId(private val value: String) : Parcelable {

    init {
        assert(value.isNotBlank()) { "Artifact id cannot be blank!" }
    }

    fun getValue() = value
}

fun String?.toArtifactIdOrNull() = if (this != null) ArtifactId(this) else null
fun String.toArtifactId() = ArtifactId(this)
