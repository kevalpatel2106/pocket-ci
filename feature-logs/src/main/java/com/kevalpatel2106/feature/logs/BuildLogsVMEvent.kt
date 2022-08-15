package com.kevalpatel2106.feature.logs

internal sealed class BuildLogsVMEvent {
    data class CreateLogFile(val fileName: String, val logs: String) : BuildLogsVMEvent()
    object ErrorSavingLog : BuildLogsVMEvent()
    object ScrollToBottom : BuildLogsVMEvent()
    object ScrollToTop : BuildLogsVMEvent()
    object Close : BuildLogsVMEvent()
}
