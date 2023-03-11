package com.kevalpatel2106.feature.drawer.usecase

import com.kevalpatel2106.feature.drawer.BottomDrawerVMEvent

internal interface HandleBottomDrawerVMEvents {
    operator fun invoke(event: BottomDrawerVMEvent)
}
