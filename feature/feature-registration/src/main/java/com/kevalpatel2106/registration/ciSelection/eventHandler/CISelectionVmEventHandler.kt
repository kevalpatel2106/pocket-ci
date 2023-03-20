package com.kevalpatel2106.registration.ciSelection.eventHandler

import com.kevalpatel2106.registration.ciSelection.model.CISelectionVMEvent

internal interface CISelectionVmEventHandler {

    operator fun invoke(event: CISelectionVMEvent)
}