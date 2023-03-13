package com.kevalpatel2106.registration.register.eventHandler

import com.kevalpatel2106.registration.register.model.RegisterVMEvent

internal interface RegisterVmEventHandler {

    operator fun invoke(event: RegisterVMEvent)
}