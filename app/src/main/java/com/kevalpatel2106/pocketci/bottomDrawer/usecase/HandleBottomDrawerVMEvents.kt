package com.kevalpatel2106.pocketci.bottomDrawer.usecase

import com.kevalpatel2106.pocketci.bottomDrawer.BottomDrawerVMEvent

internal interface HandleBottomDrawerVMEvents {
    operator fun invoke(event: BottomDrawerVMEvent)
}
