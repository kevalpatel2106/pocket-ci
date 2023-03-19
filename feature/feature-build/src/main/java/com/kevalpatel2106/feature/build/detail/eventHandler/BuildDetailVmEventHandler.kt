package com.kevalpatel2106.feature.build.detail.eventHandler

import com.kevalpatel2106.feature.build.detail.model.BuildDetailVMEvent

internal interface BuildDetailVmEventHandler {
    operator fun invoke(event: BuildDetailVMEvent)
}
