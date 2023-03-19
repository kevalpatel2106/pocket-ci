package com.kevalpatel2106.feature.build.detail.eventHandler

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.core.ui.errorHandling.showErrorSnack
import com.kevalpatel2106.core.navigation.DeepLinkDestinations
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.feature.build.detail.BuildDetailFragmentDirections
import com.kevalpatel2106.feature.build.detail.model.BuildDetailVMEvent
import com.kevalpatel2106.feature.build.detail.model.BuildDetailVMEvent.OpenBuildArtifacts
import com.kevalpatel2106.feature.build.detail.model.BuildDetailVMEvent.OpenBuildLogs
import com.kevalpatel2106.feature.build.detail.model.BuildDetailVMEvent.OpenJobs
import com.kevalpatel2106.feature.build.detail.model.BuildDetailVMEvent.OpenMarkDownViewer
import com.kevalpatel2106.feature.build.detail.model.BuildDetailVMEvent.ShowErrorAndClose
import javax.inject.Inject

internal class BuildDetailVmEventHandlerImpl @Inject constructor(
    private val fragment: Fragment
) : BuildDetailVmEventHandler {

    override fun invoke(event: BuildDetailVMEvent): Unit = with(fragment) {
        when (event) {
            is OpenBuildLogs -> findNavController().navigateToInAppDeeplink(
                DeepLinkDestinations.BuildLog(
                    accountId = event.accountId,
                    projectId = event.projectId,
                    buildId = event.buildId,
                ),
            )
            is OpenMarkDownViewer -> findNavController().navigate(
                BuildDetailFragmentDirections.actionBuildDetailToMarkdownView(
                    getString(event.titleRes),
                    event.commitMessage,
                ),
            )
            is ShowErrorAndClose -> {
                showErrorSnack(event.error)
                findNavController().navigateUp()
            }
            is OpenJobs -> findNavController().navigateToInAppDeeplink(
                DeepLinkDestinations.JobList(
                    accountId = event.accountId,
                    projectId = event.projectId,
                    buildId = event.buildId,
                    title = event.title,
                ),
            )
            is OpenBuildArtifacts -> findNavController().navigateToInAppDeeplink(
                DeepLinkDestinations.ArtifactList(
                    accountId = event.accountId,
                    projectId = event.projectId,
                    buildId = event.buildId,
                    title = event.title,
                ),
            )
        }
    }
}