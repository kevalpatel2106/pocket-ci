package com.kevalpatel2106.feature.drawer.drawer.eventHandler

import com.kevalpatel2106.feature.drawer.drawer.model.BottomDrawerVMEvent

internal interface BottomDrawerVMEventHandler {
    operator fun invoke(event: BottomDrawerVMEvent)
}
