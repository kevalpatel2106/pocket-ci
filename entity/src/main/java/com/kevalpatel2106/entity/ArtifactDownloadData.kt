package com.kevalpatel2106.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtifactDownloadData(val url: Url, val headers: Map<String, String>) : Parcelable
