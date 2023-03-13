package com.kevalpatel2106.feature.job.list.eventHandler

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.core.navigation.DeepLinkDestinations
import com.kevalpatel2106.core.navigation.navigateToInAppDeeplink
import com.kevalpatel2106.feature.job.list.model.JobListVMEvent
import com.kevalpatel2106.feature.job.list.model.JobListVMEvent.Close
import com.kevalpatel2106.feature.job.list.model.JobListVMEvent.OpenLogs
import com.kevalpatel2106.feature.job.list.model.JobListVMEvent.ShowErrorLoadingJobs
import javax.inject.Inject

internal class JobListVmEventHandlerImpl @Inject constructor(
    private val fragment: Fragment,
) : JobListVmEventHandler {

    override fun invoke(vmEvent: JobListVMEvent): Unit = with(fragment) {
        when (vmEvent) {
            is OpenLogs -> findNavController().navigateToInAppDeeplink(
                DeepLinkDestinations.BuildLog(
                    accountId = vmEvent.accountId,
                    projectId = vmEvent.projectId,
                    buildId = vmEvent.buildId,
                    jobId = vmEvent.jobId,
                ),
            )
            Close -> findNavController().navigateUp()
            is ShowErrorLoadingJobs -> {
                // TODO
            }
        }
    }
}