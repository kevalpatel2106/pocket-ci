package com.kevalpatel2106.feature.build.list.eventHandler

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.feature.build.list.BuildListFragmentDirections
import com.kevalpatel2106.feature.build.list.model.BuildListVMEvent
import com.kevalpatel2106.feature.build.list.model.BuildListVMEvent.Close
import com.kevalpatel2106.feature.build.list.model.BuildListVMEvent.OpenBuild
import com.kevalpatel2106.feature.build.list.model.BuildListVMEvent.ShowErrorLoadingBuilds
import javax.inject.Inject

internal class BuildListVmEventHandlerImpl @Inject constructor(
    private val fragment: Fragment
) : BuildListVmEventHandler {

    override fun invoke(vmEvent: BuildListVMEvent): Unit = with(fragment) {
        when (vmEvent) {
            is OpenBuild -> findNavController().navigate(
                BuildListFragmentDirections.actionBuildListToDetail(
                    accountId = vmEvent.accountId,
                    build = vmEvent.build,
                ),
            )
            Close -> findNavController().navigateUp()
            is ShowErrorLoadingBuilds -> {
                // TODO Show error
            }
        }
    }
}
