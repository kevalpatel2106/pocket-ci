package com.kevalpatel2106.feature.artifact.list.eventHandler

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.core.baseUi.showErrorSnack
import com.kevalpatel2106.core.extentions.showSnack
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.feature.artifact.list.model.ArtifactListVMEvent
import com.kevalpatel2106.feature.artifact.list.model.ArtifactListVMEvent.Close
import com.kevalpatel2106.feature.artifact.list.model.ArtifactListVMEvent.ShowArtifactRemoved
import com.kevalpatel2106.feature.artifact.list.model.ArtifactListVMEvent.ShowDownloadFailed
import com.kevalpatel2106.feature.artifact.list.model.ArtifactListVMEvent.ShowDownloadNotSupported
import com.kevalpatel2106.feature.artifact.list.model.ArtifactListVMEvent.ShowDownloadQueuedMessage
import javax.inject.Inject

internal class ArtifactListVmEventHandlerImpl @Inject constructor(
    private val fragment: Fragment
) : ArtifactListVmEventHandler {

    override operator fun invoke(vmEvent: ArtifactListVMEvent): Unit = with(fragment) {
        when (vmEvent) {
            Close -> findNavController().navigateUp()
            is ShowDownloadFailed -> {
                showErrorSnack(vmEvent.error, R.string.artifact_list_download_queue_error)
            }

            ShowDownloadQueuedMessage -> showSnack(R.string.artifact_list_download_queued_successfully)
            ShowArtifactRemoved -> showSnack(R.string.artifact_list_download_artifact_removed)
            ShowDownloadNotSupported -> showSnack(R.string.artifact_list_download_not_supported)
        }
    }
}