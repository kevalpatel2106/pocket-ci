package com.kevalpatel2106.feature.log

internal sealed class BuildLogVMEvent {
    data class CreateLogFile(val fileName: String, val logs: String) : BuildLogVMEvent()
    object ErrorSavingLog : BuildLogVMEvent()
    object ScrollToBottom : BuildLogVMEvent()
    object ScrollToTop : BuildLogVMEvent()
    object Close : BuildLogVMEvent()
}
