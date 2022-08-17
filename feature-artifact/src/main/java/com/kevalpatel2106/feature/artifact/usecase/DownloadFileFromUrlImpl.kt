package com.kevalpatel2106.feature.artifact.usecase

import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import com.kevalpatel2106.entity.Url
import javax.inject.Inject

internal class DownloadFileFromUrlImpl @Inject constructor(
    private val downloadManager: DownloadManager,
) : DownloadFileFromUrl {

    override fun invoke(url: Url, fileNameWithExtension: String) {
        downloadManager.enqueue(getDownloadRequest(url, fileNameWithExtension))
    }

    private fun getDownloadRequest(url: Url, fileName: String): DownloadManager.Request {
        // fileName -> fileName with extension
        return DownloadManager.Request(Uri.parse(url.value))
            .setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE,
            )
            .setTitle(fileName)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
    }
}
