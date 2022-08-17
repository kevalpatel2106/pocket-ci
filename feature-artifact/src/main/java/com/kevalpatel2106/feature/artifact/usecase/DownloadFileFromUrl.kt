package com.kevalpatel2106.feature.artifact.usecase

import com.kevalpatel2106.entity.Url

interface DownloadFileFromUrl {

    operator fun invoke(url: Url, fileNameWithExtension: String)
}
