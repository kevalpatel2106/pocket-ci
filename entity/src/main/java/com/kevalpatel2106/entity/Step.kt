package com.kevalpatel2106.entity

import android.os.Parcelable
import com.kevalpatel2106.entity.id.JobId
import kotlinx.parcelize.Parcelize

@Parcelize
data class Step(
    val jobId: JobId,
    val name: String,
    val status: BuildStatus,
) : Parcelable
