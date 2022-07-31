package com.kevalpatel2106.entity.id

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkflowId(val value: String) : Parcelable {

    init {
        assert(value.isNotBlank()) { "Workflow id cannot be blank value!" }
    }
}

fun String?.toWorkflowIdOrNull() = if (this != null) WorkflowId(this) else null
fun String.toWorkflowId() = WorkflowId(this)
