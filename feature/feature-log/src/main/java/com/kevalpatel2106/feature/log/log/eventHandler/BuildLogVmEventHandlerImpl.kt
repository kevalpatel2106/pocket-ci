package com.kevalpatel2106.feature.log.log.eventHandler

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kevalpatel2106.core.baseUi.showErrorSnack
import com.kevalpatel2106.core.resources.R
import com.kevalpatel2106.feature.log.log.model.BuildLogVMEvent
import com.kevalpatel2106.feature.log.log.model.BuildLogVMEvent.Close
import com.kevalpatel2106.feature.log.log.model.BuildLogVMEvent.CreateLogFile
import com.kevalpatel2106.feature.log.log.model.BuildLogVMEvent.ErrorSavingLog
import com.kevalpatel2106.feature.log.log.model.BuildLogVMEvent.InvalidateOptionsMenu
import com.kevalpatel2106.feature.log.log.usecase.LogFileSaveHelper
import javax.inject.Inject

internal class BuildLogVmEventHandlerImpl @Inject constructor(
    private val fragment: Fragment
) : BuildLogVmEventHandler {

    private val logFileSaveHelper by lazy { LogFileSaveHelper.bindWithLifecycle(fragment) }

    override operator fun invoke(vmEvent: BuildLogVMEvent): Unit = with(fragment) {
        when (vmEvent) {
            is CreateLogFile -> logFileSaveHelper.createFile(
                vmEvent.fileName,
                vmEvent.logs,
            )
            is ErrorSavingLog -> showErrorSnack(
                vmEvent.error,
                R.string.build_log_error_writing_logs,
            )
            Close -> findNavController().navigateUp()
            InvalidateOptionsMenu -> requireActivity().invalidateMenu()
        }
    }
}
