package com.kevalpatel2106.feature.artifact.usecase

import android.app.DownloadManager
import android.app.DownloadManager.Request.NETWORK_MOBILE
import android.app.DownloadManager.Request.NETWORK_WIFI
import android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
import android.net.Uri
import android.os.Environment
import com.kevalpatel2106.entity.ArtifactDownloadData
import javax.inject.Inject

internal class DownloadFileFromUrlImpl @Inject constructor(
    private val downloadManager: DownloadManager,
) : DownloadFileFromUrl {

    override fun invoke(downloadData: ArtifactDownloadData, fileNameWithExtension: String) {
        downloadManager.enqueue(getDownloadRequest(downloadData, fileNameWithExtension))
    }

    private fun getDownloadRequest(
        downloadData: ArtifactDownloadData,
        fileName: String,
    ): DownloadManager.Request {
        return DownloadManager.Request(Uri.parse(downloadData.url.value))
            .setAllowedNetworkTypes(NETWORK_WIFI or NETWORK_MOBILE)
            .setTitle(fileName)
            .setNotificationVisibility(VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)
            .apply { downloadData.headers.forEach { (key, value) -> addRequestHeader(key, value) } }
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
    }
}
