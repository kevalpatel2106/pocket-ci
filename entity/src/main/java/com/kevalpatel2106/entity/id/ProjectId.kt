package com.kevalpatel2106.entity.id

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProjectId(private val value: String) : Parcelable {

    init {
        assert(value.isNotBlank()) { "Project ID cannot be blank value!" }
    }

    fun getValue() = value
}

fun String?.toProjectIdOrNull() = if (this != null) ProjectId(this) else null
fun String.toProjectId() = ProjectId(this)
