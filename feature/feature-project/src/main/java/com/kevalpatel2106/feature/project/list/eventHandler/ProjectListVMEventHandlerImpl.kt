package com.kevalpatel2106.feature.project.list.eventHandler

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.core.navigation.DeepLinkDestinations
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.feature.project.list.model.ProjectListVMEvent
import com.kevalpatel2106.feature.project.list.model.ProjectListVMEvent.Close
import com.kevalpatel2106.feature.project.list.model.ProjectListVMEvent.OpenBuildsList
import com.kevalpatel2106.feature.project.list.model.ProjectListVMEvent.ShowErrorLoadingProjects
import javax.inject.Inject

internal class ProjectListVMEventHandlerImpl @Inject constructor(
    private val fragment: Fragment,
) : ProjectListVMEventHandler {

    override operator fun invoke(event: ProjectListVMEvent): Unit = with(fragment) {
        when (event) {
            is OpenBuildsList -> findNavController().navigateToInAppDeeplink(
                DeepLinkDestinations.BuildList(event.accountId, event.projectId),
            )

            Close -> findNavController().navigateUp()
            is ShowErrorLoadingProjects -> {
                // TODO Handle event
            }
        }
    }
}