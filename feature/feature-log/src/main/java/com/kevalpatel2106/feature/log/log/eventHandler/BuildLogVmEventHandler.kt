package com.kevalpatel2106.feature.log.log.eventHandler

import com.kevalpatel2106.feature.log.log.model.BuildLogVMEvent

internal interface BuildLogVmEventHandler {

    operator fun invoke(vmEvent: BuildLogVMEvent)
}
