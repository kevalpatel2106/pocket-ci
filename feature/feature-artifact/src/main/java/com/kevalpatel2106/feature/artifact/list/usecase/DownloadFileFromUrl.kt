package com.kevalpatel2106.feature.artifact.list.usecase

import com.kevalpatel2106.entity.ArtifactDownloadData

interface DownloadFileFromUrl {

    operator fun invoke(downloadData: ArtifactDownloadData, fileNameWithExtension: String)
}
