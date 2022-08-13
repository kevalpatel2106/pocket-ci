package com.kevalpatel2106.feature.logs

internal sealed class BuildLogsVMEvent {
    object ScrollToBottom : BuildLogsVMEvent()
    object ScrollToTop : BuildLogsVMEvent()
}
