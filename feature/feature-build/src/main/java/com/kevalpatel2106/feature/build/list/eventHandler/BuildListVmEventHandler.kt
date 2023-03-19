package com.kevalpatel2106.feature.build.list.eventHandler

import com.kevalpatel2106.feature.build.list.model.BuildListVMEvent

internal interface BuildListVmEventHandler {

    operator fun invoke(vmEvent: BuildListVMEvent)
}
