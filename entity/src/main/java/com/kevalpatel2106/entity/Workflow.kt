package com.kevalpatel2106.entity

import android.os.Parcelable
import com.kevalpatel2106.entity.id.WorkflowId
import kotlinx.parcelize.Parcelize

@Parcelize
data class Workflow(val id: WorkflowId?, val name: String) : Parcelable
