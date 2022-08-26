package com.kevalpatel2106.entity

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class CIInfo(
    val type: CIType,
    @StringRes val name: Int,
    @DrawableRes val icon: Int,
    val infoUrl: Url,
    val defaultBaseUrl: Url,
    val authTokenExplainLink: Url,
    val sampleAuthToken: Token,

    val supportCustomBaseUrl: Boolean,
    val supportViewArtifacts: Boolean,
    val supportDownloadArtifacts: Boolean,
    val supportJobs: Boolean,
    val supportBuildLevelLogs: Boolean,
    val supportJobLevelLogs: Boolean,
) : Parcelable
