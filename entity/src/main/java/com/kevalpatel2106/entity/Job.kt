package com.kevalpatel2106.entity

import android.os.Parcelable
import com.kevalpatel2106.entity.id.BuildId
import com.kevalpatel2106.entity.id.JobId
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Job(
    val id: JobId,
    val buildId: BuildId,
    val name: String,
    val triggeredAt: Date,
    val finishedAt: Date?,
    val status: BuildStatus,
    val steps: List<Step>,
) : Parcelable
