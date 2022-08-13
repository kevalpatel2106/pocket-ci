package com.kevalpatel2106.entity.id

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkflowId(private val value: String) : Parcelable {

    init {
        assert(value.isNotBlank()) { "Workflow id cannot be blank value!" }
    }

    fun getValue() = value
}

fun String?.toWorkflowIdOrNull() = if (!this.isNullOrBlank()) WorkflowId(this) else null
fun String.toWorkflowId() = WorkflowId(this)
