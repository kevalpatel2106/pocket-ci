package com.kevalpatel2106.feature.log.log.model

import com.kevalpatel2106.entity.DisplayError

internal sealed class BuildLogVMEvent {
    data class CreateLogFile(val fileName: String, val logs: String) : BuildLogVMEvent()
    data class ErrorSavingLog(val error: DisplayError) : BuildLogVMEvent()
    object Close : BuildLogVMEvent()
    object InvalidateOptionsMenu : BuildLogVMEvent()
}
